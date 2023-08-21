package com.projeto.TesteMf

import kotlin.concurrent.thread

class ThreadTest {

    companion object {
        fun run() {
            var pb = ProgressBar("Executando teste", 100.0f)
            val list = listOf(
                    "",
                    "Apagando Registros",
                    "Gerando novos registros",
                    "Migrando dados",
                    "Testando dados"
            )
            pb.present()
            for(x in 1..4)
            {
                Thread.sleep(500)
                pb.update(25.0f * x, list[x])
            }
            pb.conclude()
        }
    }


}