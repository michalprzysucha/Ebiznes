package controllers

import javax.inject._
import play.api.mvc._
import scala.collection.mutable.ListBuffer
import play.api.libs.json._
import models.Product
//import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  private val productsList = new ListBuffer[Product]()
  productsList += Product(1, "monitor", "monitor 4k", 1500.50)
  productsList += Product(2, "klawiatura", "gamingowa", 150.21)

  implicit val productsListJson: OFormat[Product] = Json.format[Product]

  def getAllProducts(): Action[AnyContent] = Action {
    if(productsList.isEmpty){
      NoContent
    } else {
      Ok(Json.toJson(productsList))
    }
  }

  def getProductById(id: Long) = Action {
    val foundProduct = productsList.find(_.id == id)
    foundProduct match {
      case Some(product) => Ok(Json.toJson(product))
      case None => NotFound
    }
  }

  def addProduct(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val body = request.body
    val jsonBodyOption: Option[JsValue] = body.asJson

    jsonBodyOption match {
      case Some(jsonBody) => {
        val nextId = productsList.map(_.id).max + 1
        val name = (jsonBody \ "name").as[String]
        val description = (jsonBody \ "description").as[String]
        val price = (jsonBody \ "price").as[Double]

        productsList += Product(nextId, name, description, price)
        Ok("Produkt dodany pomyślnie.")
      }
      case None => BadRequest("Oczekiwano ciała z danymi JSON.")
    }
  }
}