package se.kruskakli.nsomobile.nsopackage.domain

import se.kruskakli.nsomobile.nsopackage.data.NsoPackage


fun NsoPackage.toPackageUi(): PackageUi {
    return PackageUi(
        name = name,
        packageVersion = packageVersion,
        description = description,
        directory = directory,
        ncsMinVersion = ncsMinVersion,
        operStatus = if (operStatus.containsKey("up")) "up" else "-"
    )
}