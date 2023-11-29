package mhha.sample.besttastehouse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.Tm128
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import mhha.sample.besttastehouse.databinding.ActivityMainBinding
import mhha.sample.besttastehouse.databinding.BottomSheetBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), OnMapReadyCallback {//class MainActivity : AppCompatActivity()

    private lateinit var binding: ActivityMainBinding
    private lateinit var naverMap: NaverMap
    //naverMap 이 초기화 하지 않고 사용 하면 안되기 때문에 아래 코드가 필요함.
    private var isMapInit = false

    private var bestTasteHouseAdapter = BestTasteHouseListAdapter{

        collapseBottomSheet()
        moveCamera(it, 18.0) //카메라 움직임
    }

    private var markers = emptyList<Marker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mapView.onCreate(savedInstanceState)

        binding.mapView.getMapAsync(this)

        binding.bottomSheetLayout.searchResultRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = bestTasteHouseAdapter
        }

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query?.isNotEmpty() == true){
                    SearchRepository.getBestTasteHouse(query).enqueue(object : Callback<SearchResult>{
                        override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                            Log.d("response", "${response.body().toString()}")
                            val searchItemList = response.body()?.items.orEmpty()

                            if(searchItemList.isEmpty()){
                                Toast.makeText(this@MainActivity, "검색 결과가 없음",Toast.LENGTH_SHORT).show()
                                return
                            }else if(isMapInit.not()){
                                Toast.makeText(this@MainActivity, "오류 발생",Toast.LENGTH_SHORT).show()
                                return
                            }

                            //마커 삭제
                            markers.forEach {
                                it.map = null
                            }

                            markers = searchItemList.map {
                                //todo : tm128이 정상 작동하지 않으므로 원인을 찾아 수정 할 것.
                                val tm128 = Tm128(it.mapx.toDouble(),it.mapy.toDouble())
                                val latLng = tm128.toLatLng()
                                Log.d("xy", "${latLng.run { latitude }}, ${latLng.run { longitude }}")
                                Marker(LatLng(37.56701355670135, 126.9783740)).apply {
                                    captionText = it.title
                                    map = naverMap
                                }
                            }

                            bestTasteHouseAdapter.setData(searchItemList)

                            bestTasteHouseAdapter.notifyItemChanged(0, searchItemList.size)
                            collapseBottomSheet()
                            moveCamera(markers.first().position, 10.0)

                            // 아래 코드는 전체를 바꾸기 때문에 큰 데이터에서 효율적이지 못함.
                            // diffUtil을 이용해서 sameItem, sameContent 비교하여 변경하는 것이 효율적
//                            bestTasteHouseAdapter.notifyDataSetChanged()

                        }//override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>)

                        override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                            t.printStackTrace()
                        }

                    })//SearchRepository.getBestTasteHouse("서울").enqueue(object : Callback<SearchResult>
                    return false
                }
                return true
            }//override fun onQueryTextSubmit(query: String?): Boolean

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })//binding.searchView.setOnQueryTextListener(object : OnQueryTextListener

    }//override fun onCreate(savedInstanceState: Bundle?)

    private fun moveCamera(position: LatLng, zoomLevel: Double){
        if(!isMapInit){return} //에외처리

        //카메라 이동
        val cameraUpdate = CameraUpdate.scrollAndZoomTo(position, zoomLevel)
            .animate(CameraAnimation.Easing)
        naverMap.moveCamera(cameraUpdate)
    }

    private fun collapseBottomSheet(){
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetLayout.root)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
        Log.d("mapView_lifecycle","onStart")
    }//override fun onStart()

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
        Log.d("mapView_lifecycle","onResume")
    }//override fun onResume()

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
        Log.d("mapView_lifecycle","onPause")
    }//override fun onPause()

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
        Log.d("mapView_lifecycle","onStop")
    }//override fun onStop()

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
        Log.d("mapView_lifecycle","onDestroy")
    }//override fun onDestroy()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
        Log.d("mapView_lifecycle","onSaveInstanceState")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onMapReady(mapObject: NaverMap) {
        Log.d("mapView_lifecycle","onMapReady")

        //nvaerMap 초기화
        naverMap = mapObject
        isMapInit = true

        //카메라 이동
//        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.5666102, 126.9783881))
//        naverMap.moveCamera(cameraUpdate)

        //카메라 이동 시 애니메이션
//        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.5666102, 126.9783881))
//            .animate(CameraAnimation.Easing)
//        naverMap.moveCamera(cameraUpdate)

        //마커
//        val marker = Marker()
//        marker.position = LatLng(37.5670135, 126.9783740)
//        marker.map = naverMap

    }//override fun onMapReady(mapObject: NaverMap)

}//class MainActivity : AppCompatActivity(), OnMapReadyCallback


