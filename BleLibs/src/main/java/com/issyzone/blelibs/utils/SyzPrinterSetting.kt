package com.issyzone.blelibs.utils

import com.issyzone.blelibs.bean.SyzPrinter


object SyzPrinterSetting {
    //是否支持打印多份
    fun isSupportPageMore(printerType: SyzPrinter): Boolean {
        return when (printerType) {
            SyzPrinter.SYZTWOINCH -> {
                false
            }

            SyzPrinter.SYZFOURINCH -> {
                true
            }

            SyzPrinter.SYZZEROFIVE -> {
                false
            }

            else -> {
                false
            }
        }
    }

    //图片按多少字节分段
    fun getfenDuanChunkSize(printerType: SyzPrinter): Int {
        return when (printerType) {
            SyzPrinter.SYZTWOINCH -> {
                4 * 1024
            }

            SyzPrinter.SYZFOURINCH -> {
                10 * 1024
            }

            SyzPrinter.SYZZEROFIVE -> {
                4 * 1024
            }

            else -> {
                4 * 1024
            }
        }
    }


    //图片每一段按多少字节分包
     fun getchunkSizePack(printerType: SyzPrinter): Int {
         return when (printerType) {
             SyzPrinter.SYZTWOINCH -> {
                 180
             }
             SyzPrinter.SYZFOURINCH -> {
                 1 * 1024
             }

             SyzPrinter.SYZZEROFIVE -> {
                 180
             }

             else -> {
                 180
             }
         }
    }

    //判断图片每一段是否支持压缩
     fun isSupportCompress(printerType: SyzPrinter): Boolean {
        return when (printerType) {
            SyzPrinter.SYZTWOINCH -> {
                true
            }
            SyzPrinter.SYZFOURINCH -> {
                true
            }

            SyzPrinter.SYZZEROFIVE -> {
                true
            }
            else -> {
                true
            }
        }
    }




}