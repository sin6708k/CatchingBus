package com.example.catchingbus.viewmodel

import com.example.catchingbus.model.ArrivalChannel
import com.example.catchingbus.model.FavoriteRepo
import com.example.catchingbus.model.ScheduleRepo
import com.example.catchingbus.model.json.ArrivalJsonApi
import com.example.catchingbus.model.json.BusByStationJsonApi
import com.example.catchingbus.model.json.BusJsonApi
import com.example.catchingbus.model.json.CityJsonApi
import com.example.catchingbus.model.json.StationJsonApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.io.path.Path
import kotlin.time.DurationUnit
import kotlin.time.toDuration

object GlobalInitializer {
    const val SERVICE_KEY = "TdvBzglDeCrmafwGrqqjOSj7ZnRi9AR5JMUTPozZRnaaqpUTnlYtwgFWeejzhWXLvboD81F1fsk%2FtL2Br9TEjA%3D%3D"

    fun initialize(fileDirPath: String) {
        CityJsonApi.initialize(SERVICE_KEY)
        StationJsonApi.initialize(SERVICE_KEY)
        BusByStationJsonApi.initialize(SERVICE_KEY)
        BusJsonApi.initialize(SERVICE_KEY)
        ArrivalJsonApi.initialize(SERVICE_KEY)

        CoroutineScope(Dispatchers.Default).launch {
            FavoriteRepo.load(Path(fileDirPath, "favorites.txt"))
            ScheduleRepo.load(Path(fileDirPath, "schedules.txt"))

            ArrivalChannel.start(
                period = 1.toDuration(DurationUnit.MINUTES),
                remainingTimeToSend = 5.toDuration(DurationUnit.MINUTES)
            )
        }
    }
}