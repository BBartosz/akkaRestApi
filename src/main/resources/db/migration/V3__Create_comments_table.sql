CREATE TABLE "comments" (
  "id"      BIGSERIAL PRIMARY KEY,
  "user_id" BIGINT NOT NULL,
  "post_id" BIGINT NOT NULL,
  "content" TEXT NOT NULL
);

ALTER TABLE "comments" ADD CONSTRAINT "comment_user_fk" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON UPDATE NO ACTION ON DELETE CASCADE ;
ALTER TABLE "comments" ADD CONSTRAINT "comment_post_fk" FOREIGN KEY ("post_id") REFERENCES "posts" ("id") ON UPDATE NO ACTION ON DELETE CASCADE ;