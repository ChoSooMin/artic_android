package com.android.artic.ui.setting.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.artic.R
import com.android.artic.ui.setting.setting_editprofile.SettingEditProfileActivity
import com.android.artic.ui.setting.setting_version.SettingVersionActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        //프로필 수정 버튼 누르면 프로필 수정 페이지로
        setting_edit_profile_container.setOnClickListener{
            val intent= Intent(this, SettingEditProfileActivity::class.java)

            startActivity(intent)
        }

        //버전정보 누르면 버전 페이지로
        setting_version_info_container.setOnClickListener{
            val intent= Intent(this, SettingVersionActivity::class.java)
            startActivity(intent)
        }
    }
}