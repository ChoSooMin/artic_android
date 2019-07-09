package com.android.artic.ui.setting.setting_editprofile

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.media.MediaScannerConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.android.artic.R
import com.android.artic.data.MyPage
import com.android.artic.data.MyPageRequest
import com.android.artic.logger.Logger
import com.android.artic.repository.ArticRepository
import com.android.artic.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_setting_edit_profile.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import java.util.Collections.rotate

class SettingEditProfileActivity : BaseActivity() {
    private val repository: ArticRepository by inject()
    private val logger: Logger by inject()
    private var btn: TextView?= null
    private var imageview: ImageView? = null
    private lateinit var path: String

    private val GALLERY=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_edit_profile)


        btn = findViewById<View>(R.id.edit_profile_img_change_btn) as TextView
        imageview= findViewById<View>(R.id.edit_profile_img) as ImageView

        edit_profile_finish_btn.isActivated=false
        setListener()

        btn!!.setOnClickListener{
            choosePhotoFromGallary()

        }



    }

    private fun setListener() {

        edit_profile_name_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //  edit_profile_finish_btn.setTextColor(ContextCompat.getColor(btn , R.color.soft_blue))
                edit_profile_finish_btn.isActivated = true
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                edit_profile_finish_btn.isActivated = false

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        edit_profile_myinfo_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                edit_profile_finish_btn.isActivated = true
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                edit_profile_finish_btn.isActivated = false
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        edit_profile_finish_btn.setOnClickListener {
            val profile_img=path
            val name=edit_profile_name_et.text.toString()
            val my_info=edit_profile_myinfo_et.text.toString()
            repository.changeMyInfo(data= MyPageRequest(profile_img, name, my_info),
                successCallback = {
                    logger.log("token data : $it")
                    toast("success")
                },
                failCallback = {
                    toast(R.string.network_error)
                }
            )


            finish()
        }
    }

//    fun changeEditProfile() {
//        edit_profile_finish_btn.setTextColor(ContextCompat.getColor(this , R.color.soft_blue))
//    }

    //갤러리에서 이미지를 선택하면 onActivityResult() 메소드가 실행
    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }


    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                try
                {
                    var bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)

                    path = saveImage(bitmap)
                    Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show()

                    imageview!!.setImageBitmap(bitmap)
                    edit_profile_finish_btn.isActivated = true
                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        }

    }



//    private fun exifOrientationToDegrees(exifOrientation:Int): Int {
//        if(exifOrientation==ExifInterface.ORIENTATION_ROTATE_90) {
//            return 90
//        } else if(exifOrientation==ExifInterface.ORIENTATION_ROTATE_180) {
//            return 180
//        } else if(exifOrientation==ExifInterface.ORIENTATION_ROTATE_270) {
//            return 270
//        }
//        return 0
//
//    }

//    private fun rotate(bitmap:Bitmap, degrees:Int): Bitmap? {
//       if(degrees!= 0 && bitmap != null) {
//           val m = Matrix()
//           m.setRotate(degrees.toFloat(), (bitmap.width/2).toFloat(), (bitmap.height/2).toFloat())
//
//
//               val converted=Bitmap.createBitmap(bitmap, 0,0
//               , bitmap.width,bitmap.height,m,true)
//
//
//       }
//        return bitmap
//    }

    //이미지를 저장하는 메소드
    //IMAGE_DIRECTORY는 모든 이미지가 저장 될 폴더 이름
    fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        Log.d("fee",wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {

            wallpaperDirectory.mkdirs()
        }

        try
        {
            Log.d("heel",wallpaperDirectory.toString())
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                .getTimeInMillis()).toString() + ".jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    companion object {
        private val IMAGE_DIRECTORY = "/demonuts"
    }

}

