package nz.co.rossphillips.thumbnailer.thumbnailers

import java.io.InputStream
import java.io.OutputStream
import java.awt.Font
import java.awt.font.FontRenderContext
import java.awt.image.BufferedImage
import java.awt.Color
import javax.imageio.ImageIO
import java.io.ByteArrayOutputStream
import org.apache.commons.io.IOUtils
import java.awt.Graphics2D
import java.awt.RenderingHints
import org.imgscalr.Scalr._

class TextThumbnailer extends Thumbnailer {

	private val background = Color.WHITE
	private val font = new Font("Tahoma", Font.PLAIN, 10)
	private val padding = 4

	override def generateThumbnail(input: InputStream, output: OutputStream) {
		val text = IOUtils.toString(input)
		val frc = new FontRenderContext(null, true, true)
		val bounds = font.getStringBounds(text, frc)
		val image = new BufferedImage(width - padding, height - padding, BufferedImage.TYPE_INT_RGB)

		val g = image.createGraphics
		g.setColor(background)
		g.fillRect(0, 0, width, height)
		g.setColor(Color.BLACK)
		g.setFont(font)

		drawStringWrapped(g, text, bounds.getX.toInt, -bounds.getY.toInt, width, height)
		g.dispose

		ImageIO.write(pad(image, padding / 2, background), "PNG", output)
	}

	override def supportedContentTypes = Set(
		"text/plain",
		"text/cmd",
		"text/css",
		"text/csv",
		"text/html",
		"text/javascript",
		"text/vcard",
		"text/xml"
	)

	private def drawStringWrapped(g: Graphics2D, text: String, startX: Int, startY: Int, width: Int, height: Int) {
		val fontMetrics = g.getFontMetrics
		val lineHeight = fontMetrics.getHeight
		var x = startX
		var y = startY

		def newLine { y += lineHeight; x = startX }

		// TODO - break if (y > height) ???
		text.split("\n|\r\n").foreach { line =>
			line.split(" ").foreach { word =>
				val wordWidth = fontMetrics.stringWidth(word + " ")

				g.drawString(word, x, y)

				if (x + wordWidth >= startX + width) newLine
				else x += wordWidth
			}

			newLine
		}
	}

}