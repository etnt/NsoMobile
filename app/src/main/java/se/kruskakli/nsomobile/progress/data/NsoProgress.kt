package se.kruskakli.nsomobile.progress.data


/*
  /restconf/data/tailf-progress:progress?unhide=debug

{
  "tailf-progress:progress": {
    "trace": [
      {
        "name": "datacenter",
        "destination": {
          "oper-data": [null]
        },
        "enabled": true,
        "verbosity": "normal",
        "event": [
          {
            "timestamp": "2024-03-19T18:09:26.941294+00:00",
            "timer": "0.0",
            "trace-id": "c4f13924-7a43-4e4a-8cef-36b51f57c7ca",
            "span-id": "aadd55d7f1ef6c6f",
            "parent-span-id": "516300ff9515d404",
            "session-id": "50",
            "transaction-id": "1102",
            "datastore": "ietf-datastores:running",
            "context": "cli",
            "message": "check configuration policies"
          }
        ]
      }
    ]
  }
}
*/

data class NsoProgress(
    val tailf-progress:progress: Progress
)