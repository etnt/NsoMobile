package se.kruskakli.nsomobile.debug.allocators.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
{
  "nso-dbg:allocators": {
    "carriers-size": "225435648",
    "blocks-size": "156588816",
    "utilization": "69.5",
    "mbcs": {
      "carriers-size": "191840256",
      "blocks-size": "123058992",
      "utilization": "64.1"
    },
    "sbcs": {
      "carriers-size": "33595392",
      "blocks-size": "33529824",
      "utilization": "99.8"
    },
    "allocator": [
      {
        "name": "binary_alloc",
        "instance": 0,
        "carriers-size": "2129920",
        "blocks-size": "758920",
        "utilization": "35.6",
        "mbcs": {
          "carriers-size": "2129920",
          "blocks-size": "758920",
          "utilization": "35.6"
        },
        "sbcs": {
          "carriers-size": "0",
          "blocks-size": "0",
          "utilization": "100.0"
        }
      }
    ]
  }
}
 */

@Serializable
data class NsoAllocators(
    val allocator: List<Allocator>,
    @SerialName("blocks-size") val blocksSize: String,
    @SerialName("carriers-size") val carriersSize: String,
    val utilization: String,
    val mbcs: SizeInfo,
    val sbcs: SizeInfo
)

@Serializable
data class Allocators(
    @SerialName("nso-dbg:allocators") val nsoAllocators: NsoAllocators
)
