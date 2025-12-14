package com.example.playlist_maker_android_nikolotovayulia.data//package com.example.playlist_maker_android_nikolotovayulia.data
//
//import com.example.playlist_maker_android_nikolotovayulia.domain.BaseResponse
//import com.example.playlist_maker_android_nikolotovayulia.domain.NetworkClient
//import com.example.playlist_maker_android_nikolotovayulia.domain.TracksSearchRequest
//import com.example.playlist_maker_android_nikolotovayulia.domain.TracksSearchResponse
//
//class RetrofitNetworkClient(private val storage: Storage) : NetworkClient {
//    override fun doRequest(request: Any): TracksSearchResponse {
//        val searchList = storage.search((request as TracksSearchRequest).expression)
//        return TracksSearchResponse(searchList).apply { resultCode = 200 }
//    }
//}
