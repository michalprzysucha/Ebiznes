package controllers

import models.{AddPayment, Payment}

import javax.inject._
import play.api.libs.json.Json
import play.api.mvc._

import scala.collection.mutable

@Singleton
class PaymentController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private val paymentList = new mutable.ListBuffer[Payment]()

  def getPayments: Action[AnyContent] = Action { implicit request =>
    Ok(Json.toJson(paymentList))
  }

  def addPayment(): Action[AnyContent] = Action { implicit request =>
    val content = request.body
    val jsonObject = content.asJson

    val payment: Option[AddPayment] =
      jsonObject.flatMap(
        Json.fromJson[AddPayment](_).asOpt
      )

    payment match {
      case Some(newPayment) =>
        val addedPayment = Payment(newPayment.value)
        paymentList += addedPayment
        Created(Json.toJson(addedPayment))
      case None => BadRequest
    }
  }

}