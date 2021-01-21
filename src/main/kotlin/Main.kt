import ddf.minim.AudioPlayer
import ddf.minim.Minim
import processing.core.PApplet

class Main: PApplet() {

    private lateinit var minim: Minim
    private lateinit var player: AudioPlayer

    override fun setup() {
        this.minim = Minim(this)
        this.player = minim.loadFile("sound/bgm.mp3")
        this.player.play()
    }

    override fun draw() {
        background(0)
    }

    override fun stop() {
        this.player.close() // サウンドデータ終了
        this.minim.stop()
        super.stop()
    }
    fun run() = PApplet.main(Main::class.java.simpleName)
}

fun main() = Main().run()
