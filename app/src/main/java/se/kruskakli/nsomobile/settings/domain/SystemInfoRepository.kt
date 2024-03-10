package se.kruskakli.nsomobile.settings.domain

interface SystemInfoRepository {
   fun getSystemInfo(): SystemInfo?
   fun setSystemInfo(systemInfo: SystemInfo)
}

class SystemInfoRepositoryImpl : SystemInfoRepository {
    private var systemInfo: SystemInfo? = null

   override fun getSystemInfo(): SystemInfo? {
       return systemInfo
   }

   override fun setSystemInfo(systemInfo: SystemInfo) {
       this.systemInfo = systemInfo
   }
}