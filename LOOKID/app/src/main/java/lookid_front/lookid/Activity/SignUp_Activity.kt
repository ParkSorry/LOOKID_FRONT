package lookid_front.lookid.Activity

import android.app.AlertDialog
<<<<<<< HEAD
import android.content.DialogInterface
import android.graphics.Color
=======
>>>>>>> d285b8a4e1fbb9783320514a6335f9c216ea90e9
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
<<<<<<< HEAD
import com.google.gson.Gson
=======
>>>>>>> d285b8a4e1fbb9783320514a6335f9c216ea90e9
import kotlinx.android.synthetic.main.activity_signup.*
import lookid_front.lookid.Dialog.Loading_Dialog
import lookid_front.lookid.Control.Okhttp
import lookid_front.lookid.Control.Json
<<<<<<< HEAD
import lookid_front.lookid.Dialog.Address_Dialog
import lookid_front.lookid.Dialog.Bank_Dialog
import lookid_front.lookid.Entity.Reservation_Entity
=======
>>>>>>> d285b8a4e1fbb9783320514a6335f9c216ea90e9
import lookid_front.lookid.Entity.User_Entity
import lookid_front.lookid.R
import org.json.JSONObject

class SignUp_Activity : AppCompatActivity() {
    var user : User_Entity? = null
    var checkId : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        //Id_edittext 리스너 장착
        signup_id_EditText.addTextChangedListener(EditListener())

<<<<<<< HEAD
=======
        //bank_name_spinner init
        val bank_list  = resources.getStringArray(R.array.bank_list)
        val bank_name_spinner_adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_item,bank_list)
        bank_name_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        signup_bank_name_Spinner.adapter = bank_name_spinner_adapter
>>>>>>> d285b8a4e1fbb9783320514a6335f9c216ea90e9
    }

    inner class SignUp_Control{
        fun edit_check() : Boolean{
            //필수 사항 EditText id값 array
<<<<<<< HEAD
            val editArray = arrayListOf<Int>(R.id.signup_name_EditText, R.id.signup_pw_EditText, R.id.signup_pw2_EditText, R.id.signup_name_EditText,
=======
            var editArray = arrayListOf<Int>(R.id.signup_name_EditText, R.id.signup_pw_EditText, R.id.signup_pw2_EditText, R.id.signup_name_EditText,
>>>>>>> d285b8a4e1fbb9783320514a6335f9c216ea90e9
                    R.id.signup_phone_EditText, R.id.signup_email_EditText)

            //해당 EditText 값이 비었는지 체크
            for(i in 0 until editArray.size){
<<<<<<< HEAD
                val editText : EditText = findViewById(editArray[i])
=======
                var editText : EditText = findViewById(editArray[i])
>>>>>>> d285b8a4e1fbb9783320514a6335f9c216ea90e9
                if(editText.text.isNullOrEmpty()) {
                    Toast.makeText(applicationContext,"필수 정보를 입력해주세요",Toast.LENGTH_SHORT).show()
                    return false
                }
            }

            //1차 비밀번호와 2차 비밀번호 같은지 확인
            if(!signup_pw_EditText.text.toString().equals(signup_pw2_EditText.text.toString())){
                Toast.makeText(applicationContext,"1차 2차 비밀번호가 다릅니다",Toast.LENGTH_SHORT).show()
                return false
            }

            return true
        }

        fun GET_Check(id : String){
            val url = getString(R.string.server_url) + getString(R.string.check_id) + id
            asynctask().execute("0",url)
        }

        fun POST_SignUp(pw : String){
            val url = getString(R.string.server_url) + getString(R.string.sign_up)
            asynctask().execute("1",url,pw)
        }

        fun Dialog_Signup(){
            var builder = AlertDialog.Builder(this@SignUp_Activity)
            builder.setMessage("회원가입 성공")
            builder.setCancelable(false)
            builder.setPositiveButton("확인") { dialog, which -> finish() }
            builder.show()
        }
<<<<<<< HEAD

        fun Dialog_search_Address(){
            val address_Dialog = Address_Dialog(this@SignUp_Activity, signup_address_TextView)
            address_Dialog.show()
        }

        fun Dialog_bankname(){
            val bank_Dialog = Bank_Dialog(this@SignUp_Activity,signup_bank_name_TextView.text.toString())
            bank_Dialog.setOnShowListener(Dialog_Listener())
            bank_Dialog.show()
        }
=======
>>>>>>> d285b8a4e1fbb9783320514a6335f9c216ea90e9
    }

    inner class asynctask : AsyncTask<String, Void, String>(){
        var state : Int = -1 //state == 0 : GET_아이디 중복확인, state == 1 : POST_회원가입
        var loadingDialog = Loading_Dialog(this@SignUp_Activity)

        override fun onPreExecute() {
            loadingDialog.show()
        }
        override fun doInBackground(vararg params: String): String {
            state = Integer.parseInt(params[0])
            var url = params[1]
            var response : String = ""

            when (state){
                0->response = Okhttp().GET(url)
                1->{
                    val pw = params[2] //3번째 파라미터 pw : String
                    response = Okhttp().POST(url, Json().signup(user,pw))
                }
            }
            return response
        }

        override fun onPostExecute(response: String) {
            if(response.isEmpty()) {
                Log.d("SignUp_Activity", "null")
                loadingDialog.dismiss()
                return
            }
            if(!Json().isJson(response)){
                Toast.makeText(applicationContext,"네트워크 통신 오류", Toast.LENGTH_SHORT).show()
                Log.d("SignUp_Activity",response)
                loadingDialog.dismiss()
                return
            }

            val jsonObj = JSONObject(response)
            when (state){
                0->{
                    if(jsonObj.getBoolean("success")){//중복이 안됨
                        Toast.makeText(applicationContext,"사용가능한 아이디입니다",Toast.LENGTH_SHORT).show()
                        checkId = true
                        //signup_signup_Button.isEnabled = true
                    }
                    else
                        Toast.makeText(applicationContext, "사용불가능한 아이디입니다",Toast.LENGTH_SHORT).show()
                }
                1->{
                    if(jsonObj.getBoolean("success")){//회원가입 성공
                        //다이얼로그 띄움
                        SignUp_Control().Dialog_Signup()
                    }
                    else{
                        Toast.makeText(applicationContext,"회원가입 실패",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            loadingDialog.dismiss()
        }
    }

<<<<<<< HEAD
    inner class Dialog_Listener : DialogInterface.OnShowListener{
        override fun onShow(dialog: DialogInterface?) {
            val dialog = dialog as Bank_Dialog
            val bank_check_Button = dialog.findViewById<Button>(R.id.dialog_bank_check_Button)
            bank_check_Button.setOnClickListener {
                if(!dialog.getBank().isNullOrEmpty()) {
                    signup_bank_name_TextView.text = dialog.getBank()
                    dialog.dismiss()
                }
                else
                    Toast.makeText(applicationContext,"은행을 선택해주세요",Toast.LENGTH_SHORT).show()
            }
        }
    }

=======
>>>>>>> d285b8a4e1fbb9783320514a6335f9c216ea90e9
    //Activity 클릭 리스너
    fun signup_Click_Listener(view :View){
        when(view.id){
            R.id.signup_idcheck_Button ->{
<<<<<<< HEAD
                val id = signup_id_EditText.text.toString()
=======
                var id = signup_id_EditText.text.toString()
>>>>>>> d285b8a4e1fbb9783320514a6335f9c216ea90e9
                if (id.isEmpty())
                    Toast.makeText(applicationContext, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
                else {
                    SignUp_Control().GET_Check(id)
                }
            }
            R.id.signup_signup_Button ->{
                if(!checkId) { //아이디 중복체크 완료여부 체크
                    Toast.makeText(applicationContext,"중복확인이 필요합니다",Toast.LENGTH_SHORT).show()
                    return
                }

                if(SignUp_Control().edit_check()){ //필수 입력 정보 체크
                    val id = signup_id_EditText.text.toString()
                    val name = signup_name_EditText.text.toString()
                    val phone = signup_phone_EditText.text.toString()
                    val email = signup_email_EditText.text.toString()
<<<<<<< HEAD
                    val address = signup_address_TextView.text.toString()
                    val address_detail = signup_addressDet_EditText.text.toString()
                    val bank_name = signup_bank_name_TextView.text.toString()
=======
                    val address = signup_address_EditText.text.toString()
                    val bank_name = signup_bank_name_Spinner.selectedItem.toString()
>>>>>>> d285b8a4e1fbb9783320514a6335f9c216ea90e9
                    val bank_number = signup_bank_number_EditText.text.toString()
                    val bank_holder = signup_bank_holder_EditText.text.toString()
                    val pw = signup_pw2_EditText.text.toString()
                    user = User_Entity(id,name,phone,email,address,bank_name,bank_number,bank_holder)
<<<<<<< HEAD
                    user!!.address_detail = address_detail
=======
>>>>>>> d285b8a4e1fbb9783320514a6335f9c216ea90e9

                    SignUp_Control().POST_SignUp(pw)
                }
            }
<<<<<<< HEAD
            R.id.signup_bank_name_ImageButton->SignUp_Control().Dialog_bankname()
            R.id.signup_findadd_Button->SignUp_Control().Dialog_search_Address()
=======
>>>>>>> d285b8a4e1fbb9783320514a6335f9c216ea90e9
        }
    }

    inner class EditListener : TextWatcher {
<<<<<<< HEAD
        override fun afterTextChanged(s: Editable?) { signup_idcheck_Button.isEnabled = !s.isNullOrEmpty() }
=======
        override fun afterTextChanged(s: Editable?) { }
>>>>>>> d285b8a4e1fbb9783320514a6335f9c216ea90e9
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            checkId = false
            //signup_signup_Button.isEnabled = false
        }
    }
}
