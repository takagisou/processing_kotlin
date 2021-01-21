import ddf.minim.AudioOutput
import ddf.minim.AudioPlayer
import ddf.minim.Minim
import ddf.minim.ugens.Oscil
import ddf.minim.ugens.Waves
import processing.core.PApplet

enum class Sound {
    BGM,
    SE;

    val filePath: String
        get() = when(this) {
            BGM -> "sound/bgm.mp3"
            SE -> "sound/gun.wav"
        }
}

class Main: PApplet() {

    private lateinit var minim: Minim
    private lateinit var output: AudioOutput
    private lateinit var wave: Oscil
    private lateinit var player: AudioPlayer

    override fun setup() {
        this.minim = Minim(this)
        this.output = minim.lineOut

        wave = Oscil(440f, 0.5f, Waves.SINE)
        wave.patch(output)
    }

    override fun settings() {
        size(512, 200)
    }

    override fun draw() {
        background(255)

        stroke(255f, 0f, 0f)
        strokeWeight(3f);
        noFill()
        beginShape()
        for(i in 0 until width) {
            vertex(i.toFloat(), height / 2 - wave.waveform.value(i.toFloat() / width) * 100)
        }
        endShape()

        stroke(0)
        strokeWeight(1f)
        noFill()
        beginShape()
        for(i in 0 until output.bufferSize()) {
            vertex(i.toFloat(), height / 2 + output.mix.get(i) * 100)
        }
        endShape()
    }

    override fun mouseMoved() {
        val amp = map(mouseY.toFloat(), 0f, height.toFloat(), 1f, 0f)
        wave.setAmplitude(amp)
        val freq = map(mouseX.toFloat(), 0f, width.toFloat(), 0f, 15000f)
        wave.setFrequency(freq)
    }

    override fun mousePressed() = saveFrame("images/image.jpg")

    override fun stop() {
        this.player.close() // サウンドデータ終了
        this.minim.stop()
        super.stop()
    }

    override fun keyPressed() {
        wave.waveform = when(key) {
            '1' -> Waves.SINE
            '2' -> Waves.TRIANGLE
            '3' -> Waves.SAW
            '4' -> Waves.SQUARE
            '5' -> Waves.QUARTERPULSE
            '6' -> Waves.PHASOR
            else -> Waves.SINE
        }
    }

    fun run() = PApplet.main(Main::class.java.simpleName)
}

fun main() = Main().run()
