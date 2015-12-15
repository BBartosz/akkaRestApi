import akka.http.scaladsl.server.Directives._
import api.{CommentsApi, PostsApi, ApiErrorHandler, UsersApi}

trait Routes extends ApiErrorHandler with UsersApi with PostsApi with CommentsApi{
  val routes =
    pathPrefix("v1") {
      usersApi ~
      postsApi ~
      commentsApi
    } ~ path("")(getFromResource("public/index.html"))
}
