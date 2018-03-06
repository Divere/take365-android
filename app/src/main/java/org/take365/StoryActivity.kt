package org.take365

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.FileProvider
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.crashlytics.android.Crashlytics
import kotlinx.android.synthetic.main.activity_story.*
import org.take365.adapters.StoryRecycleAdapter
import org.take365.components.GridAutofitLayoutManager
import org.take365.components.ImageUploader
import org.take365.components.ProgressRequestBody
import org.take365.components.SpacesItemDecoration
import org.take365.helpers.DpToPixelsConverter
import org.take365.models.StoryDay
import org.take365.network.models.responses.BaseResponse
import org.take365.network.models.responses.story.GetStoryDetailsResponse
import org.take365.network.models.StoryDetails
import org.take365.network.models.StoryImage
import org.take365.network.models.StoryListItem
import org.take365.views.StoryDayView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileNotFoundException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class StoryActivity : Take365Activity() {

    private var currentStory: StoryListItem? = null
    private var storyInfo: StoryDetails? = null
    private var isContributingStory: Boolean = false

    private var imagesByDays: HashMap<String, StoryImage>? = null
    private var days: MutableList<StoryDay>? = null
    private var sections: TreeMap<String, MutableList<StoryDay>>? = null

    private var storyRecycleAdapter: StoryRecycleAdapter? = null

    private var todayString: String? = null
    private var selectedDate: String? = null

    private var pictureImagePath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        currentStory = intent.getSerializableExtra("story") as StoryListItem
        title = currentStory!!.title

        setSupportActionBar(toolbar)

        if (currentStory!!.progress.isOutdated) {
            fab.visibility = View.GONE
        }

        fab.setOnClickListener(object : View.OnClickListener {
            private fun captureImage() {

                val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val imageFileName = timeStamp + ".jpg"
                val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                pictureImagePath = storageDir.absolutePath + "/" + imageFileName
                val file = File(pictureImagePath)
                val outputFileUri = FileProvider.getUriForFile(this@StoryActivity, this@StoryActivity.applicationContext.packageName + ".provider", file)

                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)

                val resInfoList = this@StoryActivity.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                for (resolveInfo in resInfoList) {
                    val packageName = resolveInfo.activityInfo.packageName
                    this@StoryActivity.grantUriPermission(packageName, outputFileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }

                if (intent.resolveActivity(packageManager) != null) {
                    startActivityForResult(intent, CAMERA_REQUEST)
                }
            }

            override fun onClick(view: View) {
                if (imagesByDays != null && imagesByDays!![todayString] != null) {
                    showAskDialog("Данное действие заменит уже существующую фотографию", DialogInterface.OnClickListener { dialog, which -> captureImage() })
                    return
                }

                captureImage()
            }
        })

        val df = SimpleDateFormat("yyyy-MM-dd")
        todayString = df.format(Date())

        refreshStoryInfo()

        val permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        val progressCallback = object : ProgressRequestBody.UploadCallbacks {
            override fun onProgressUpdate(percentage: Int) {
                storyRecycleAdapter!!.setUploadProgress(selectedDate!!, percentage)
            }

            override fun onError() {

            }

            override fun onFinish() {

            }
        }

        var tmpFile: File? = null

        val resultCallback = object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (!response.isSuccessful) {
                    showApiError(response)
                }

                refreshStoryInfo()
                cleanUp()
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                showConnectionError()
                cleanUp()
            }

            private fun cleanUp() {
                storyRecycleAdapter!!.setUploadProgress(selectedDate!!, 0)
                tmpFile?.delete()
                tmpFile = null
            }
        }

        when (requestCode) {
            CAMERA_REQUEST -> {
                val imgFile = File(pictureImagePath)
                if (!imgFile.exists()) {
                    return
                }
                selectedDate = todayString
                ImageUploader.uploadImage(currentStory!!.id, imgFile, selectedDate, progressCallback, resultCallback)
            }
            PICK_IMAGE -> {
                try {
                    val inputStream = this@StoryActivity.contentResolver.openInputStream(data?.data!!)
                    tmpFile = File.createTempFile("take365","")
                    val buffer = ByteArray(inputStream.available())
                    inputStream.read(buffer)
                    tmpFile!!.writeBytes(buffer)
                    ImageUploader.uploadImage(currentStory!!.id, tmpFile!!, selectedDate, progressCallback, resultCallback)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }

            }
        }
    }

    private fun refreshStoryInfo() {
        Take365App.getApi().getStoryDetails(currentStory!!.id).enqueue(object : Callback<GetStoryDetailsResponse> {
            override fun onResponse(call: Call<GetStoryDetailsResponse>, responseGet: Response<GetStoryDetailsResponse>) {
                if (!responseGet.isSuccessful) {
                    showApiError(responseGet)
                    return
                }

                storyInfo = responseGet.body()?.result
                try {
                    renderStoryInfo()
                } catch (e: ParseException) {
                    Crashlytics.logException(e)
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<GetStoryDetailsResponse>, t: Throwable) {
                showConnectionError()
            }
        })
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(ParseException::class)
    private fun renderStoryInfo() {
        imagesByDays = HashMap()
        days = ArrayList()
        sections = TreeMap(Comparator { o1, o2 ->
            val df = SimpleDateFormat("yyyy-MM")

            var date1: Date? = null
            var date2: Date? = null

            try {
                date1 = df.parse(o1)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            try {
                date2 = df.parse(o2)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            date2!!.compareTo(date1)
        })

        for (author in storyInfo!!.authors) {
            if (author.id == Take365App.getCurrentUser().id) {
                isContributingStory = true
                break
            }
        }

        for (image in storyInfo!!.images) {
            imagesByDays!!.put(image.date, image)
        }

        val df = SimpleDateFormat("yyyy-MM-dd")

        val dateStart = df.parse(storyInfo!!.progress.dateStart)
        val dateEnd = df.parse(storyInfo!!.progress.dateEnd)
        val today = Date()

        val calendar = Calendar.getInstance()
        calendar.time = dateStart

        val firstDay = StoryDay()
        firstDay.day = df.format(calendar.time)
        firstDay.image = imagesByDays?.let { imagesByDays!![firstDay.day] }

        if (isContributingStory) {
            days!!.add(0, firstDay)
        }

        for (i in 0..storyInfo!!.progress.passedDays + 1 - 1) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            val currentDate = calendar.time
            if (currentDate.compareTo(dateEnd) > 0 || currentDate.compareTo(today) > 0) {
                break
            }

            val storyDay = StoryDay()
            storyDay.day = df.format(currentDate)
            storyDay.image = imagesByDays!![storyDay.day]

            if (storyDay.image == null && !isContributingStory) {
                continue
            }

            days!!.add(0, storyDay)
        }

        for (storyDay in days!!) {
            val sectionTitleComponents = storyDay.day.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val sectionTitle = sectionTitleComponents[0] + "-" + sectionTitleComponents[1]

            var sectionContent: MutableList<StoryDay>? = sections!![sectionTitle]

            if (sectionContent == null) {
                sectionContent = ArrayList()
                sections!!.put(sectionTitle, sectionContent)
            }

            sectionContent.add(storyDay)
        }

        if (storyRecycleAdapter != null) {
            storyRecycleAdapter!!.setSections(sections!!)
            storyRecycleAdapter!!.notifyDataSetChanged()
            return
        }


        storyRecycleAdapter = StoryRecycleAdapter(this, sections!!, View.OnClickListener { v ->
            val day = (v as StoryDayView).storyDay
            selectedDate = day.day

            if (day.image != null) {
                val playerIntent = Intent(this@StoryActivity, PhotoPlayerActivity::class.java)
                playerIntent.putExtra("currentItem", day)
                playerIntent.putExtra("items", days as ArrayList<StoryDay>?)
                startActivity(playerIntent)
                return@OnClickListener
            }

            captureImage()
        }, if (!currentStory!!.progress.isOutdated)
            View.OnLongClickListener { v ->
                val day = (v as StoryDayView).storyDay
                selectedDate = day.day

                if (day.image != null) {
                    showAskDialog("Данное действие заменит уже существующую фотографию", DialogInterface.OnClickListener { dialog, which -> captureImage() })
                }

                true
            }
        else
            null)

        //        val spacing = (gvDays.width - DpToPixelsConverter.toPixels(100) * 3) / 6

        val gridLayoutManager = GridAutofitLayoutManager(this, DpToPixelsConverter.toPixels(105))
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (storyRecycleAdapter!!.getItemViewType(position) == StoryRecycleAdapter.ElementsType.VIEW_HEADER) gridLayoutManager.spanCount else 1
            }
        }

        gvDays.layoutManager = gridLayoutManager
        gvDays.addItemDecoration(SpacesItemDecoration(DpToPixelsConverter.toPixels(10)))
        gvDays.adapter = storyRecycleAdapter
    }

    private fun captureImage() {
        if (currentStory!!.progress.isOutdated) {
            return
        }

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
    }

    companion object {
        private val CAMERA_REQUEST = 1888
        private val PICK_IMAGE = 1889
        private val REQUEST_EXTERNAL_STORAGE = 1
        private val PERMISSIONS_STORAGE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        fun startActivity(context: Context, storyListItem: StoryListItem) {
            val storyIntent = Intent(context, StoryActivity::class.java)
            storyIntent.putExtra("story", storyListItem)
            context.startActivity(storyIntent)
        }
    }
}