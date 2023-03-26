package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import scala.collection.mutable.ListBuffer
import play.api.libs.json._
import models.{Product}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  private val productsList = new ListBuffer[Product]()
  productsList += Product(1, "monitor", "monitor 4k", 1500.50)
  productsList += Product(2, "klawiatura", "gamingowa", 150.21)

  implicit val productsListJson = Json.format[Product]

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

  def addProduct = Action.async(parse.json) { implicit request: Request[JsValue] =>
    request.body.validate[Product].map { product =>
      productId => Created(Json.toJson(productId))
        productsList += product

    }.getOrElse(Future.successful(BadRequest("Invalid Product")))
  }

//  def createProduct = Action.async(parse.json) { implicit request =>
////    val content = request.body
////    val jsonObject = content.asJson
////    val product: Option[Product] =
////      jsonObject.flatMap(
////        Json.fromJson[Product](_).asOpt
////      )
////
////    product match {
////      case Some(newProduct) =>
////        val nextId = productsList.map(_.id).max + 1
////        val toBeAdded = TodoListItem(nextId, newProduct.name, newProduct.description, newProduct.price)
////        productsList += toBeAdded
////        Created(Json.toJson(toBeAdded))
////      case None =>
////        BadRequest
//
//    request.body.validate[Product].fold(
//      error => Future.successful(BadRequest("Invalid JSON")),
//      product => {
//        productsList += product
//        Created(Json.toJson(product))
//      }
//    )
//  }
}