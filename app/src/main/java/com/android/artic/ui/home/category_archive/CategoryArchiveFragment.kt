package com.android.artic.ui.home.category_archive


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.android.artic.R
import com.android.artic.data.Archive
import com.android.artic.logger.Logger
import com.android.artic.repository.ArticRepository
import com.android.artic.ui.archive.ArchiveActivity
import kotlinx.android.synthetic.main.fragment_category_archive.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Display Category Archive List (2x2)
 * Must create this with param!
 * @param categoryId it need for get server data
 * @param categoryName it display fragment text box
 * @author greedy0110
 */
class CategoryArchiveFragment(
    private var categoryId: Int = 0,
    private var categoryName: String = "Dummy"
) : Fragment() {
    private val logger: Logger by inject()

    private val repository: ArticRepository by inject()
    private lateinit var adapter: CategoryArchiveCardAdapter

    private lateinit var txtCategoryArchiveName: TextView
    private lateinit var rvCategoryArchive: RecyclerView
    private lateinit var containerArchive: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_category_archive, container, false)

        // kotlin extension 으로 하면 fragment 여러개 추가할때 오류 발생
        txtCategoryArchiveName = view.findViewById(R.id.txt_category_archive_name)
        rvCategoryArchive = view.findViewById(R.id.rv_category_archive)
        containerArchive = view.findViewById(R.id.linear_fragment_category_archive_category)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.run {
            adapter = CategoryArchiveCardAdapter(this, listOf(), categoryName)

            txtCategoryArchiveName.text = categoryName
            rvCategoryArchive.adapter = adapter

            // 2x2 를 만들어줘야 하므로 데이터는 앞의 4개만 받아오자.
            rvCategoryArchive.layoutManager = GridLayoutManager(this, 2)

            // @수민) 카테고리 누르면 해당 카테고리에 해당하는 아카이브 리스트가 있는 페이지로
            containerArchive.setOnClickListener {
                var intent = Intent(this, ArchiveActivity::class.java)

                intent.putExtra("category_idx", categoryId)
                intent.putExtra("category_name", categoryName)

                startActivity(intent)
            }
        }
    }

    // 홈으로 다시 돌아왔을 때 카테고리별 갱신
    override fun onResume() {
        super.onResume()

        activity?.run {
            repository.getArchiveListGivenCategory(
                categoryId = categoryId,
                successCallback = {

                    if (it.isEmpty()) {
                        supportFragmentManager.beginTransaction().remove(this@CategoryArchiveFragment).commit()
                        adapter.notifyDataSetChanged()
                    }
                    logger.log("category fragment $categoryId $categoryName ${it.take(4)}")
                    if (it.isEmpty()) supportFragmentManager.beginTransaction().remove(this@CategoryArchiveFragment).commit()
                    // 최신 4개의 archive 만 가져온다!
                    it.take(4).let { cut->
                        adapter.data = cut
                        adapter.notifyDataSetChanged()
                    }
                }
            )
        }
    }
}
