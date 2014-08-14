import com.ferntastic.api._
import org.scalatra._
import javax.servlet.ServletContext
import com.ferntastic.api.service.MongoDBAdapter

class ScalatraBootstrap extends LifeCycle {
  	override def init(context: ServletContext) {

		val mongoDB = new MongoDBAdapter()

		context.mount(new FerntasticAPI(mongoDB), "/*")
  	}
}
