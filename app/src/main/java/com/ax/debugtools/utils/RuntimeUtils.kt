package com.ax.debugtools.utils

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader


object RuntimeUtils {
    fun execCommands(commands: List<String>, isRoot: Boolean): String {
        val process: Process = Runtime.getRuntime().exec(if (isRoot) "su" else "sh")
        //执行命令
        DataOutputStream(process.outputStream).use {
            for (command: String in commands) {
                it.write(command.toByteArray())
                it.writeBytes("\n")
                it.flush()
            }
            it.writeBytes("exit\n")
            it.flush()
        }
        process.waitFor()
        //获取执行信息
        val sb = StringBuilder()
        BufferedReader(InputStreamReader(process.inputStream)).use {
            for (line in it.readLine()) {
                sb.append(line)
            }
        }
        process.destroy()
        return sb.toString()
    }
}