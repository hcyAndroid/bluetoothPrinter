package com.issyzone.blelibs.fmBeans

import com.issyzone.blelibs.exception.BleException
import com.issyzone.blelibs.upacker.Upacker
import com.orhanobut.logger.Logger

//0 代表  notify打开失败
//1 代表  notify 打开成功
//2 代表  notify 蓝牙发送数据来了
data class FmNotifyBean(
    var notifyState: Int = 0, var exception: BleException? = null, var byteArray: ByteArray? = null
) {

    private fun bleDeviceError(mpRespondMsg: MPMessage.MPRespondMsg) {
        val deviceErrorInfo = MPMessage.MPCodeMsg.parseFrom(mpRespondMsg.error.toByteArray())
        Logger.e("设备问题>>>>${deviceErrorInfo.toString()}")
    }

    fun getFmDeviceInfo() {
        val mpRespondMsg = MPMessage.MPRespondMsg.parseFrom(byteArray)
        Logger.i("NOTIFY返回信息>>>>${mpRespondMsg.toString()}")
        when (mpRespondMsg.eventType) {
            MPMessage.EventType.DEVICEPRINT->{
                if (mpRespondMsg.code == 200) {
                    val deviceInfo =
                        MPMessage.MPCodeMsg.parseFrom(mpRespondMsg.respondData.toByteArray())
                    Logger.d("打印图片主动上报>>>>${deviceInfo.toString()}")
                } else {
                    if (mpRespondMsg.respondData!=null){
                        val deviceInfo =
                            MPMessage.MPCodeMsg.parseFrom(mpRespondMsg.respondData.toByteArray())
                        Logger.e("打印图片出错主动上报>>>>${deviceInfo.toString()}")
                        bleDeviceError(mpRespondMsg)
                    }

                }
            }
            MPMessage.EventType.DEVICEREPORT -> {
                /**
                 *
                10: 打印开盖关盖状态    info:   1: 合盖,  2:开盖
                11: 打印机纸张状态      info:   1: 上纸,  2:缺纸  3:卡纸
                12: 打印机打印头状态    info:   1: 恢复正常 2:过热
                13: 打印机电量状态      info:   电池电量,如果当前电量只剩下20%,那么传20
                300: 打印机打印状态     info:    1: 打印成功  2: 打印取消 3.打印失败
                400: 打印机升级状态     info:    1: 升级陈工,三秒后打印机重启 2.升级失败
                 */
                if (mpRespondMsg.code == 200) {
                    val deviceInfo =
                        MPMessage.MPCodeMsg.parseFrom(mpRespondMsg.respondData.toByteArray())
                    Logger.d("设备状况主动上报>>>>${deviceInfo.toString()}")
                } else {
                    bleDeviceError(mpRespondMsg)
                }
            }

            MPMessage.EventType.DEVICEINFO -> {
                //打印设备信息
                if (mpRespondMsg.code == 200) {
                    val deviceInfo =
                        MPMessage.MPDeviceInfoMsg.parseFrom(mpRespondMsg.respondData.toByteArray())
                    Logger.d("获取设备信息>>>>${deviceInfo.toString()}")
                } else {
                    bleDeviceError(mpRespondMsg)
                }
            }

            MPMessage.EventType.CLOSETIME -> {
                //设置关机的返回
                if (mpRespondMsg.code == 200) {
                    val deviceInfo =
                        MPMessage.MPDeviceInfoMsg.parseFrom(mpRespondMsg.respondData.toByteArray())
                    Logger.d("设置关机>>>>${deviceInfo.toString()}")
                } else {
                    bleDeviceError(mpRespondMsg)
                }
            }

            else -> {



            }
        }

    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FmNotifyBean

        if (notifyState != other.notifyState) return false
        if (exception != other.exception) return false
        if (byteArray != null) {
            if (other.byteArray == null) return false
            if (!byteArray.contentEquals(other.byteArray)) return false
        } else if (other.byteArray != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = notifyState
        result = 31 * result + (exception?.hashCode() ?: 0)
        result = 31 * result + (byteArray?.contentHashCode() ?: 0)
        return result
    }
}



