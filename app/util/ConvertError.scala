package util

object ConvertError {
    private val email = "email"
    private val password = "password"
    private val confirm_password = "confirm_password"

    private val min = "error.minLength"
    private val required = "error.required"
    private val invalid = "invalid"

    def convert(k: String, v: String): (String, String) = {
        (k match {
            case `email` => "メールアドレス"
            case `password` => "パスワード"
            case `confirm_password` => "確認パスワード"
            case _ => k
        },
        v match {
            case `min` => "短すぎます"
            case `required` => "必須項目です"
            case `invalid` => "違います"
            case _ => v
        })
    }
}