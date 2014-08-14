import com.ferntastic.api._
import org.scalatra._
import javax.servlet.ServletContext
import com.ferntastic.api.service.MongoDB

class ScalatraBootstrap extends LifeCycle {
  	override def init(context: ServletContext) {

		val mongoDB = new MongoDB()

		context.mount(new FerntasticAPI(mongoDB), "/*")
  	}
}
