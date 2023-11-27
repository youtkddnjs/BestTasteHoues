package mhha.sample.besttastehoues

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import mhha.sample.besttastehoues.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnMapReadyCallback {//class MainActivity : AppCompatActivity()

    private lateinit var binding: ActivityMainBinding
    private lateinit var naverMap: NaverMap
    //naverMap 이 초기화 하지 않고 사용 하면 안되기 때문에 아래 코드가 필요함.
    private var isMapInit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mapView.onCreate(savedInstanceState)

        binding.mapView.getMapAsync(this)



    }//override fun onCreate(savedInstanceState: Bundle?)

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
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.5666102, 126.9783881))
        naverMap.moveCamera(cameraUpdate)

        //카메라 이동 시 애니메이션
//        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.5666102, 126.9783881))
//            .animate(CameraAnimation.Easing)
//        naverMap.moveCamera(cameraUpdate)

        //마커
        val marker = Marker()
        marker.position = LatLng(37.5670135, 126.9783740)
        marker.map = naverMap

    }//override fun onMapReady(mapObject: NaverMap)

}//class MainActivity : AppCompatActivity(), OnMapReadyCallback