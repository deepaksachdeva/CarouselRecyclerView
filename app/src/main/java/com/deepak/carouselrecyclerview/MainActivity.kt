package com.deepak.carouselrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var dateAdapter: DateAdapter? = null
    var firstItemWidthDate: Float = 0.toFloat()
    var paddingDate: Float = 0.toFloat()
    var itemWidthDate: Float = 0.toFloat()
    var allPixelDate: Int = 0
    var finalWidthDate: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerViewCount = findViewById<RecyclerView>(R.id.recyclerViewCarousel)
        if(recyclerViewCount != null) {
            recyclerViewCount.postDelayed( {setDateValue()}, 300)
            recyclerViewCount.postDelayed( {setDateValue()}, 5000)
        }

        val vtoDate = recyclerViewCount.viewTreeObserver
        vtoDate.addOnPreDrawListener(object: ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                recyclerViewCount.viewTreeObserver.removeOnPreDrawListener(this)
                finalWidthDate = recyclerViewCount.measuredWidth
                itemWidthDate = resources.getDimension(R.dimen.dob_width)
                paddingDate = (finalWidthDate - itemWidthDate) /2
                firstItemWidthDate  = paddingDate
                allPixelDate = 0

                val dateLayoutManager = CenterZoomLayoutManager(this@MainActivity,
                    LinearLayoutManager.HORIZONTAL, false)
                recyclerViewCount.layoutManager = dateLayoutManager

                recyclerViewCount.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        synchronized(this) {
                            if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                                calculatePositionAndScrollDate(recyclerView)
                            }
                        }
                    }

                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        allPixelDate += dx
                    }
                })
                if(labelerDates == null) {
                    labelerDates = ArrayList()
                }

                getLabelerDate()
                dateAdapter = DateAdapter(labelerDates!!, firstItemWidthDate.toInt())
                recyclerViewCount.adapter = dateAdapter
                dateAdapter!!.setSelectionItem(dateAdapter!!.itemCount - 1)

                return true
            }
        })
    }

    var labelerDates: java.util.ArrayList<LabelerDate>? = java.util.ArrayList()
    fun getLabelerDate() {
        for(i in 0..31) {
            val labelerDate = LabelerDate()
            labelerDate.number = Integer.toString(i)
            labelerDates!!.add(labelerDate)
            if(i == 0 || i == 31) {
                labelerDate.type = DateAdapter.VIEW_TYPE_PADDING
            } else {
                labelerDate.type = DateAdapter.VIEW_TYPE_ITEM
            }
        }
    }

    private fun calculatePositionAndScrollDate(recyclerView: RecyclerView) {
        var expectedPositionDate = Math.round((allPixelDate + paddingDate - firstItemWidthDate) / itemWidthDate)

        if(expectedPositionDate == -1) {
            expectedPositionDate = 0
        } else if (expectedPositionDate >= recyclerView.adapter?.itemCount!! - 2) {
            expectedPositionDate--
        }
        scrollListToPositionDate(recyclerView, expectedPositionDate)
    }

    private fun scrollListToPositionDate(recyclerView: RecyclerView, expectedPositionDate: Int) {
        val targetScrollPosDate = expectedPositionDate * itemWidthDate + firstItemWidthDate - paddingDate
        val missingPxDate = targetScrollPosDate - allPixelDate
        if(missingPxDate != 0f) {
            recyclerView.smoothScrollBy(missingPxDate.toInt(), 0)
        }
        setDateValue()
    }

    private fun setDateValue() {
        val expectedPositionDateColor = Math.round((allPixelDate + paddingDate - firstItemWidthDate) / itemWidthDate)
        val setColorDate = expectedPositionDateColor + 1
        dateAdapter?.setSelectionItem(setColorDate)
    }
}