package com.example.backup

import android.graphics.ImageFormat
import android.graphics.Point
import android.security.identity.EphemeralPublicKeyNotFoundException
import com.example.backup.constants.CellConstants
import com.example.backup.constants.FieldConstants
import com.example.backup.models.Block
import com.example.backup.helper.array2OfByte
import com.example.backup.models.Shape
import com.example.backup.storage.AppPreference
import java.text.FieldPosition

class AppModel {
    enum class Statuses {
        AWAITING_START, ACTIVE, INACTIVE, OVER
    }

    enum class Motions {
        LEFT, RIGHT, DOWN, ROTATE
    }

    var score = 0
    private var preference: AppPreference? = null

    var currentBlock: Block? = null
    var currentState: String = Statuses.AWAITING_START.name

    private var field: Array<ByteArray> = array2OfByte(
        FieldConstants.COLUMN_COUNT.value,
        FieldConstants.ROW_COUNT.value
    )

    fun setPreference(preference: AppPreference?) {
        this.preference = preference
    }

    fun getCellStatus(row: Int, column: Int): Byte? {
        return field[row][column]
    }

    private fun setCellStatus(row: Int, column: Int, status: Byte?) {
        if (status != null)
            field[row][column] = status
    }

    fun isGameOver(): Boolean {
        return currentState == Statuses.OVER.name
    }

    fun isGameActive(): Boolean {
        return currentState == Statuses.ACTIVE.name
    }

    fun isGameAwaitingStart(): Boolean {
        return currentState == Statuses.AWAITING_START.name
    }

    private fun boostScore() {
        score += 10
        if (score > preference?.getHighScore() as Int)
            preference?.saveHighScore(score)
    }

    private fun generateNextBlock() {
        currentBlock = Block.createBlock()
    }

    private fun validTranslation(position: Point, shape: Array<ByteArray>): Boolean {
        return if (position.y < 0 || position.x < 0)
            false
        else (if (position.y + shape.size > FieldConstants.ROW_COUNT.value)
            false
        else if (position.x + shape.size > FieldConstants.COLUMN_COUNT.value)
            false
        else
            for (i in 0 until shape.size)
                for (j in 0 until shape[i].size) {
                    val y = position.y + i
                    val x = position.x + j
                    if (CellConstants.EMPTY.value != shape[i][j] && CellConstants.EMPTY.value != field[y][x])
                        return false
                }) as Boolean
        true
    }

    private fun moveValid(position: Point, frameNumber: Int?): Boolean {
        val shape: Array<ByteArray>? = currentBlock?.getShape(frameNumber as Int)

        return validTranslation(position, shape as Array<ByteArray>)
    }

    fun ganerateField(action: String) {
        if (isGameActive()) {
            resetField()
            var frameNumber: Int? = currentBlock?.frameNumber
            val coordinate: Point? = Point()
            coordinate?.x = currentBlock?.position?.x
            coordinate?.y = currentBlock?.position?.y

            when (action) {
                Motions.LEFT.name ->
                    coordinate?.x = currentBlock?.position?.x?.minus(1)
                Motions.RIGHT.name ->
                    coordinate?.x = currentBlock?.position?.x?.plus(1)
                Motions.DOWN.name ->
                    coordinate?.y = currentBlock?.position?.y?.plus(1)
                Motions.ROTATE.name -> {
                    frameNumber = frameNumber?.plus(1)

                    if (frameNumber != null)
                        if (frameNumber >= currentBlock?.frameCount as Int)
                            frameNumber = 0
                }
            }

            if (moveValid(coordinate as Point, frameNumber)) {
                translateBlock(currentBlock?.position as Point, currentBlock?.frameNumber as Int)

                if (Motions.DOWN.name == action) {
                    boostScore()
                    persistCellData()
                    assessField()
                    generateNextBlock()
                    if (!blockAdditionPossible()) {
                        currentState = Statuses.OVER.name
                        currentBlock = null
                        resetField(false)
                    }
                } else {
                    if (frameNumber != null) {
                        translateBlock(coordinate, frameNumber)
                        currentBlock = null
                        currentBlock?.setState(frameNumber, coordinate)
                    }
                }
            }
        }
    }

    private fun resetField(ephemeralCellOnly: Boolean = true) {
        for (i in 0 until FieldConstants.ROW_COUNT.value) {
            (0 until FieldConstants.COLUMN_COUNT.value).filter {
                !ephemeralCellOnly || field[i][it] ==
                        CellConstants.EPHEMERAL.value
            }.forEach { field[i][it] = CellConstants.EMPTY.value }
        }
    }

    private fun persistCellData() {
        for (i in 0 until field.size)
            for (j in 0 until field[i].size) {
                var status = getCellStatus(i, j)

                if (status == CellConstants.EPHEMERAL.value) {
                    status = currentBlock?.staticValue
                    setCellStatus(i, j, status)
                }
            }
    }

    private fun assessField() {
        for (i in 0 until field.size) {
            var emptyCells = 0

            for (j in 0 until field[i].size) {
                val status = getCellStatus(i, j)

                val isEmpty = CellConstants.EMPTY.value == status

                if (isEmpty)
                    emptyCells++
            }
            if (emptyCells == 0)
                shiftRows(i)
        }
    }

    private fun translateBlock(position: Point, frameNumber: Int) {
        synchronized(field) {
            val shape: Array<ByteArray>? = currentBlock?.getShape(frameNumber)

            if (shape != null)
                for (i in shape.indices)
                    for (j in 0 until shape[i].size) {
                        val y = position.y + i
                        val x = position.x + j
                        if (CellConstants.EMPTY.value != shape[i][j])
                            field[y][x] = shape[i][j]
                    }
        }
    }

    private fun blockAdditionPossible():Boolean {
        if(!moveValid(currentBlock?.position as Point,currentBlock?.frameNumber))
            return false
        return true
    }

    private fun shiftRows(i: Int) {
        if(i>0)
            for(j in i-1 downTo 0)
                for (m in 0 until field[j].size)
                    setCellStatus(j+1,m,getCellStatus(j,m))
    }

    fun startGame(){
        if(!isGameActive()){
            currentState=Statuses.ACTIVE.name
            generateNextBlock()
        }
    }

    fun restartGame(){
        reserModel()
        startGame()
    }

    fun endGame(){
        score=0
        currentState=Statuses.AWAITING_START.name
    }

    private fun reserModel() {
        resetField(false)
        score=0
        currentState=Statuses.AWAITING_START.name
    }
}