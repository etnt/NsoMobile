package se.kruskakli.nsomobile.syscounters.domain


data class SysCountersUi(
    val transaction: TransactionUi?,
    val serviceConflicts: ServiceConflictsUi?,
    val cdb: CdbUi?,
    val device: DeviceUi?,
    val session: SessionUi?
) {
    data class TransactionUi(
        val datastore: List<DatastoreUi?> = emptyList()
    ) {
        data class DatastoreUi(
            val name: String,
            val commit: String,
            val totalTime: String?,
            val serviceTime: String?,
            val validationTime: String?,
            val globalLockTime: String?,
            val globalLockWaitTime: String?,
            val aborts: String?,
            val conflicts: String?,
            val retries: String?
        ) {
            companion object {
                const val NAME_DESCRIPTION = "Datastore name: 'operational' or 'running'"
                const val COMMIT_DESCRIPTION = "Total number of committed transactions."
                const val TOTAL_TIME_DESCRIPTION = "Total amount of milliseconds spent in successful transactions."
                const val SERVICE_TIME_DESCRIPTION = "Total amount of milliseconds spent in service execution."
                const val VALIDATION_TIME_DESCRIPTION = "Total amount of milliseconds spent in transaction validation."
                const val GLOBAL_LOCK_TIME_DESCRIPTION = "Total amount of milliseconds spent in global lock."
                const val GLOBAL_LOCK_WAIT_TIME_DESCRIPTION = "Total amount of milliseconds spent waiting for the global lock."
                const val ABORTS_DESCRIPTION = "Total number of aborted transactions."
                const val CONFLICTS_DESCRIPTION = "Total number of transaction conflicts for running."
                const val RETRIES_DESCRIPTION = "Total number of transaction retries for running."
            }
        }
    }

    data class ServiceConflictsUi(
        val serviceType: List<ServiceTypeUi?>
    ) {
        data class ServiceTypeUi(
            val name: String?,
            val conflicts: String?
        ) {
            companion object {
                const val NAME_DESCRIPTION = "Type of the service."
                const val CONFLICTS_DESCRIPTION = "Number of transaction conflicts with this service type."
            }
        }
    }

    data class CdbUi(
        val compactions: String?,
        val compaction: CompactionUi?,
        val bootTime: String?,
        val phase0Time: String?,
        val phase1Time: String?,
        val phase2Time: String?
    ) {
        companion object {
            const val COMPACT_DESCRIPTION = "Total number of CDB compactions."
            const val BOOT_TIME_DESCRIPTION = "Total amount of time spent in boot."
            const val PHASE0_TIME_DESCRIPTION = "Total amount of time spent in startup phase0."
            const val PHASE1_TIME_DESCRIPTION = "Total amount of time spent in startup phase1."
            const val PHASE2_TIME_DESCRIPTION = "Total amount of time spent in startup phase2."
        }
        data class CompactionUi(
            val ACdb: String?,
            val OCdb: String?,
            val SCdb: String?,
            val total: String?
        ) {
            companion object {
                const val A_CDB_DESCRIPTION = "Number of CDB configuration datastore compactions."
                const val O_CDB_DESCRIPTION = "Number of CDB operational datastore compactions."
                const val S_CDB_DESCRIPTION = "Number of CDB snapshot datastore compactions."
                const val TOTAL_DESCRIPTION = "Total number of CDB compactions."
            }
        }
    }

    data class DeviceUi(
        val connect: String?,
        val connectFailed: String?,
        val syncFrom: String?,
        val syncTo: String?,
        val outOfSync: String?
    ) {
        companion object {
            const val CONNECT_DESCRIPTION = "Total number of device connects."
            const val CONNECT_FAILED_DESCRIPTION = "Total number of failed device connects."
            const val SYNC_FROM_DESCRIPTION = "Total number of sync-from invocations."
            const val SYNC_TO_DESCRIPTION = "Total number of sync-to invocations."
            const val OUT_OF_SYNC_DESCRIPTION = "Total number of out of sync detections."
        }
    }

    data class SessionUi(
        val total: String?,
        val netconfTotal: String?,
        val restconfTotal: String?,
        val jsonrpcTotal: String?,
        val snmpTotal: String?,
        val cliTotal: String?
    ) {
        companion object {
            const val TOTAL_DESCRIPTION = "Total number of northbound sessions completed."
            const val NETCONF_TOTAL_DESCRIPTION = "Total number of NETCONF sessions completed."
            const val RESTCONF_TOTAL_DESCRIPTION = "Total number of RESTCONF sessions completed."
            const val JSONRPC_TOTAL_DESCRIPTION = "Total number of JSONRPC sessions completed."
            const val SNMP_TOTAL_DESCRIPTION = "Total number of SNMP sessions completed."
            const val CLI_TOTAL_DESCRIPTION = "Total number of CLI sessions completed."
        }
    }
}

