package se.kruskakli.nsomobile.debug.allocators.domain

import se.kruskakli.nsomobile.debug.allocators.data.Allocator
import se.kruskakli.nsomobile.debug.allocators.data.NsoAllocators
import se.kruskakli.nsomobile.debug.allocators.data.SizeInfo

fun NsoAllocators.toAllocatorUi(): AllocatorUi {
    return AllocatorUi(
        allocators = allocator.map { it.toNsoAllocator() },
        blocksSize = blocksSize,
        carriersSize = carriersSize,
        utilization = utilization,
        mbcs = mbcs.toSizeInfoUi(),
        sbcs = sbcs.toSizeInfoUi()
    )
}

fun Allocator.toNsoAllocator(): AllocatorUi.NsoAllocator {
    return AllocatorUi.NsoAllocator(
        name = name,
        instance = instance.toString(),
        blocksSize = blocksSize,
        carriersSize = carriersSize,
        utilization = utilization,
        mbcs = mbcs.toSizeInfoUi(),
        sbcs = sbcs.toSizeInfoUi()
    )
}

fun SizeInfo.toSizeInfoUi(): AllocatorUi.SizeInfoUi {
    return AllocatorUi.SizeInfoUi(
        blocksSize = blocksSize,
        carriersSize = carriersSize,
        utilization = utilization
    )
}
