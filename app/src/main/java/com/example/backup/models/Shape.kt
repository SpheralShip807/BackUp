package com.example.backup.models

import java.lang.IllegalArgumentException

enum class Shape(val frameCount: Int, val startPosition: Int) {
    TetrominoI(2, 2) {
        override fun getFrame(frameNumber: Int): Frame {
            return when (frameNumber) {
                0 -> Frame(4).addRow("1111")
                1 -> Frame(1)
                    .addRow("1")
                    .addRow("1")
                    .addRow("1")
                    .addRow("1")
                else -> throw IllegalArgumentException("${frameNumber} is an invalid frame number")
            }
        }
    },
    TetrominoO(1, 1) {
        override fun getFrame(frameNumber: Int): Frame {
            return Frame(2)
                .addRow("11")
                .addRow("11")
        }
    },
    TetrominoZ(2, 1) {
        override fun getFrame(frameNumber: Int): Frame {
            return when (frameNumber) {
                0 -> Frame(3)
                    .addRow("110")
                    .addRow("011")
                1 -> Frame(2)
                    .addRow("01")
                    .addRow("11")
                    .addRow("10")
                else -> throw IllegalArgumentException("${frameNumber} is an invalid frame number")
            }
        }
    },
    TetrominoS(2, 1) {
        override fun getFrame(frameNumber: Int): Frame {
            return when (frameNumber) {
                0 -> Frame(3)
                    .addRow("011")
                    .addRow("110")
                1 -> Frame(2)
                    .addRow("10")
                    .addRow("11")
                    .addRow("01")
                else -> throw IllegalArgumentException("${frameNumber} is an invalid frame number")
            }
        }
    },
    TetrominoT(2, 1) {
        override fun getFrame(frameNumber: Int): Frame {
            return when (frameNumber) {
                0 -> Frame(3)
                    .addRow("010")
                    .addRow("111")
                1 -> Frame(2)
                    .addRow("10")
                    .addRow("11")
                    .addRow("10")
                2->Frame(3)
                    .addRow("111")
                    .addRow("010")
                3->Frame(2)
                    .addRow("01")
                    .addRow("11")
                    .addRow("01")
                else -> throw IllegalArgumentException("${frameNumber} is an invalid frame number")
            }
        }
    },
    TetrominoJ(2, 1) {
        override fun getFrame(frameNumber: Int): Frame {
            return when (frameNumber) {
                0 -> Frame(3)
                    .addRow("100")
                    .addRow("111")
                1 -> Frame(2)
                    .addRow("11")
                    .addRow("10")
                    .addRow("10")
                2->Frame(3)
                    .addRow("111")
                    .addRow("001")
                3->Frame(2)
                    .addRow("01")
                    .addRow("01")
                    .addRow("11")
                else -> throw IllegalArgumentException("${frameNumber} is an invalid frame number")
            }
        }
    },
    TetrominoL(2, 1) {
        override fun getFrame(frameNumber: Int): Frame {
            return when (frameNumber) {
                0 -> Frame(3)
                    .addRow("001")
                    .addRow("111")
                1 -> Frame(2)
                    .addRow("11")
                    .addRow("01")
                    .addRow("01")
                2->Frame(3)
                    .addRow("111")
                    .addRow("100")
                3->Frame(2)
                    .addRow("10")
                    .addRow("10")
                    .addRow("11")
                else -> throw IllegalArgumentException("${frameNumber} is an invalid frame number")
            }
        }
    };

    abstract fun getFrame(frameNumber: Int): Frame
}