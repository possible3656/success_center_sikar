package com.winbee.successcentersikar.WebApi;



import com.winbee.successcentersikar.NewModels.AboutModel;
import com.winbee.successcentersikar.NewModels.ContentSyllabu;
import com.winbee.successcentersikar.NewModels.CourseContent;
import com.winbee.successcentersikar.NewModels.CourseModel;
import com.winbee.successcentersikar.NewModels.LiveChatMessage;
import com.winbee.successcentersikar.NewModels.LiveMessage;
import com.winbee.successcentersikar.NewModels.LogOut;
import com.winbee.successcentersikar.NewModels.PaymentModel;
import com.winbee.successcentersikar.NewModels.PdfContent;
import com.winbee.successcentersikar.NewModels.PdfSell;
import com.winbee.successcentersikar.NewModels.SubjectContent;
import com.winbee.successcentersikar.NewModels.TestSeriesPayment;
import com.winbee.successcentersikar.NewModels.TestSubscription;
import com.winbee.successcentersikar.NewModels.TestTopRanker;
import com.winbee.successcentersikar.NewModels.TopicContent;
import com.winbee.successcentersikar.NewModels.TxnModel;
import com.winbee.successcentersikar.model.AskDoubtQuestion;
import com.winbee.successcentersikar.model.BannerModel;
import com.winbee.successcentersikar.model.FacultyName;
import com.winbee.successcentersikar.model.ForgetMobile;
import com.winbee.successcentersikar.model.InstructionsModel;
import com.winbee.successcentersikar.model.LiveClass;
import com.winbee.successcentersikar.model.LiveStatus;
import com.winbee.successcentersikar.model.McqAskedQuestionModel;
import com.winbee.successcentersikar.model.McqQuestionModel;
import com.winbee.successcentersikar.model.McqQuestionSolutionModel;
import com.winbee.successcentersikar.model.McqSolutionModel;
import com.winbee.successcentersikar.model.NewDoubtQuestion;
import com.winbee.successcentersikar.model.NotesModel;
import com.winbee.successcentersikar.model.OtpVerify;
import com.winbee.successcentersikar.model.RefCode;
import com.winbee.successcentersikar.model.RefUser;
import com.winbee.successcentersikar.model.ResendOtp;
import com.winbee.successcentersikar.model.ResetPassword;
import com.winbee.successcentersikar.model.ResultModel;
import com.winbee.successcentersikar.model.SIACDetailsMainModel;
import com.winbee.successcentersikar.model.SIADMainModel;
import com.winbee.successcentersikar.model.SIADSolutionMainModel;
import com.winbee.successcentersikar.model.SectionDetailsMainModel;
import com.winbee.successcentersikar.model.SolutionDoubtQuestion;
import com.winbee.successcentersikar.model.SolutionQuestion;
import com.winbee.successcentersikar.model.StartTestModel;
import com.winbee.successcentersikar.model.UpdateModel;
import com.winbee.successcentersikar.model.ViewResult;

import org.json.JSONArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ClientApi {

    @POST("fetch-cover-banner.php")
    Call<ArrayList<BannerModel>> getBanner(
            @Query("org_id") String org_id
    );

    @POST("about.php")
    Call<AboutModel> getAbout(
    );
    @POST("fetch_bucket_cover_information.php")
    Call<CourseContent> getBranchId(
            @Query("SubURL") int SubURL,
            @Query("ORG_ID") String ORG_ID,
            @Query("PARENT_ID") String PARENT_ID,
            @Query("USER_ID") String USER_ID,
            @Query("DEVICE_ID") String DEVICE_ID
    );

    @POST("fetch_bucket_cover_information.php")
    Call<SubjectContent> getCourseSubject(
            @Query("SubURL") int SubURL,
            @Query("USER_ID") String USER_ID,
            @Query("ORG_ID") String ORG_ID,
            @Query("PARENT_ID") String PARENT_ID,
            @Query("DEVICE_ID") String DEVICE_ID
    );
    @POST("fetch-course-syllabus.php")
    Call<ArrayList<ContentSyllabu>> getCourseContent(
            @Query("org_id") String org_id,
            @Query("parent_id") String parent_id
    );

    @POST("fetch_bucket_cover_information.php")
    Call<TopicContent> getCourseTopic(
            @Query("SubURL") int SubURL,//2
            @Query("USER_ID") String USER_ID,
            @Query("ORG_ID") String ORG_ID,
            @Query("PARENT_ID") String PARENT_ID,
            @Query("DEVICE_ID") String DEVICE_ID
    );


    @POST("fetch_bucket_cover_information.php")
    Call<PdfContent> getCoursePdf(
            @Query("SubURL") int SubURL,//5
            @Query("USER_ID") String USER_ID,
            @Query("ORG_ID") String ORG_ID,
            @Query("PARENT_ID") String PARENT_ID,
            @Query("DEVICE_ID") String DEVICE_ID
    );


    //login
    @POST("fetch_user_cover_information.php")
    Call<RefCode> refCodeSignIn(
            @Query("SubURL") int SubURL,
            @Query("username") String username,
            @Query("password") String password,
            @Query("refcode") String refcode,
            @Query("IMEI") String IMEI
    );

    //logout
    @POST("fetch_user_cover_information.php")
    Call<LogOut> refCodeLogout(
            @Query("SubURL") int SubURL,//3
            @Query("username") String username,
            @Query("password") String password,
            @Query("refcode") String refcode,
            @Query("IMEI") String IMEI
    );

    //force login
    @POST("fetch_user_cover_information.php")
    Call<RefCode> refCodeForceLogout(
            @Query("SubURL") int SubURL,//4
            @Query("username") String username,
            @Query("password") String password,
            @Query("refcode") String refcode,
            @Query("IMEI") String IMEI
    );


    @POST("user_registration_information.php")
    Call<RefUser> refUserSignIn(
            @Query("SubURL") int SubURL,
            @Query("name") String name,
            @Query("email") String email,
            @Query("mobile") String mobile,
            @Query("refcode") String refcode,
            @Query("password") String password,
            @Query("DEVICE_ID") String DEVICE_ID
    );

    @POST("send-otp.php")
    Call<ForgetMobile> getForgetMobile(
            @Query("SubURL") int SubURL,
            @Query("username") String username
    );

    @POST("send-otp.php")
    Call<ResendOtp> getResendOtp(
            @Query("username") String username,
            @Query("SubURL") int SubURL
    );


    @POST("reset-password.php")
    Call<ResetPassword> getResetPassword(
            @Query("SubURL") int SubURL,
            @Query("username") String username,
            @Query("otp") String otp,
            @Query("new_password") String new_password
    );
    @POST("verify-otp.php")
    Call<OtpVerify> getOtpVerify(
            @Query("SubURL") int SubURL,
            @Query("username") String username,
            @Query("otp") String otp
    );


    @POST("fetch-all-live-classes.php")
    Call<ArrayList<LiveClass>> getLive();

    @POST("fetch-notes-of-live-class.php")
    Call<ArrayList<NotesModel>> getNotes(
            @Query("live_class_id") String live_class_id
    );

    @POST("fetch-daily-update.php")
    Call<ArrayList<UpdateModel>> getDailyupdate();



    @FormUrlEncoded
    @POST("ask-doubt.php")
    Call<NewDoubtQuestion> getNewQuestion(
            @Field("title") String title,
            @Field("question") String question,
            @Field("userid") String userid
    );

    //for quiz
    @FormUrlEncoded
    @POST("ask-doubt-quiz.php")
    Call<NewDoubtQuestion> getQuizQuestion(
            @Field("title") String title,
            @Field("question") String question,
            @Field("userid") String userid
    );

    @POST("beta-doubt-storage.php")
    Call<ArrayList<AskDoubtQuestion>> getQuestion();

    @POST("beta-doubt-storage-quiz.php")
    Call<ArrayList<AskDoubtQuestion>> getQuizQuestion();

    @FormUrlEncoded
    @POST("ask-doubt-quiz.php")
    Call<SolutionDoubtQuestion> getQuizSolutionText(
            @Field("userid") String userid,
            @Field("filename") String filename,
            @Field("answer") String answer,
            @Field("solutionType") String solutionType
    );

    @FormUrlEncoded
    @POST("ask-doubt.php")
    Call<SolutionDoubtQuestion> getNewSolutionText(
            @Field("userid") String userid,
            @Field("filename") String filename,
            @Field("answer") String answer,
            @Field("solutionType") String solutionType
    );

    @FormUrlEncoded
    @POST("ask-doubt.php")
    Call<SolutionDoubtQuestion> getNewSolutionImage(
            @Field("userid") String userid,
            @Field("filename") String filename,
            @Field("answer") String answer,
            @Field("solutionType") String solutionType
    );

    @FormUrlEncoded
    @POST("ask-doubt-quiz.php")
    Call<SolutionDoubtQuestion> getQuizSolutionImage(
            @Field("userid") String userid,
            @Field("filename") String filename,
            @Field("answer") String answer,
            @Field("solutionType") String solutionType
    );

    @POST("beta-doubt-storage.php")
    Call<ArrayList<SolutionQuestion>> getSolution(
            @Query("filename") String filename
    );

    @POST("beta-doubt-storage-quiz.php")
    Call<ArrayList<SolutionQuestion>> getQuizSolution(
            @Query("filename") String filename
    );



    @POST("fetch-section-details.php")
    Call<SectionDetailsMainModel> fetchSectionDetails(
            @Query("org_code") String org_code,
            @Query("auth_code") String auth_code,
            @Query("userId") String userId
    );
    @POST("fetch_pdf_data.php")
    Call<PdfSell> fetchPdf(
            @Query("ORG_ID") String ORG_ID,
            @Query("USER_ID") String USER_ID,
            @Query("DEVICE_ID") String DEVICE_ID
    );

    @POST("fetch-section-subscription-plan.php")
    Call<TestSubscription> getSubscriptionDetails(
            @Query("ExamSectionId") String ExamSectionId,
            @Query("auth_code") String auth_code
    );

    @POST("fetch-section-individual-assessment-cover-details.php")
    @FormUrlEncoded
    Call<SIACDetailsMainModel> fetchSIACDetails(
            @Field("org_code") String org_code,
            @Field("auth_code") String auth_code,
            @Field("bucket_code") String bucket_code,
            @Field("user_code") String user_code
    );

    @POST("fetch-top-scorers.php")
    @FormUrlEncoded
    Call<TestTopRanker> fetchTopRanker(
            @Field("PaperID") String PaperID,
            @Field("UserID") String UserID
    );

    @POST("fetch-section-individual-assessment-data.php")
    @FormUrlEncoded
    Call<SIADMainModel> fetchSIADDATA(
            @Field("org_code") String org_code,
            @Field("auth_code") String auth_code,
            @Field("bucket_code") String bucket_code,
            @Field("paper_code") String paper_code
    );

    @POST("Submit-Exam-Paper.php")
    @FormUrlEncoded
    Call<ResultModel> submitResponse(
            @Field("CoachingID") String CoachingID,
            @Field("PaperID") String PaperID,
            @Field("UserID") String UserID,
            @Field("Response") JSONArray jsonArray,
            @Field("Staticstics") String Staticstics,
            @Field("Submit_Exam_Paper") boolean Submit_Exam_Paper
    );


    @POST("Start-Exam-Paper.php")
    @FormUrlEncoded
    Call<StartTestModel> getStartTest(
            @Field("OrgID") String OrgID,
            @Field("UserID") String UserID,
            @Field("PaperID") String PaperID,
            @Field("ExamStatus") String ExamStatus,
            @Field("Read_Instruction") String Read_Instruction
    );

    @POST("view-result.php")
    @FormUrlEncoded
    Call<ViewResult> viewResult(
            @Field("OrgID") String OrgID,
            @Field("PaperID") String PaperID,
            @Field("UserID") String UserID
    );


    @POST("insert_mcq.php")
    Call<McqQuestionModel> mcqQuestionYes(
            @Query("UserId") String UserId,
            @Query("Question") String Question,
            @Query("QuestionTitle") String QuestionTitle,
            @Query("Opt1") String Opt1,
            @Query("Opt2") String Opt2,
            @Query("Opt3") String Opt3,
            @Query("Opt4") String Opt4,
            @Query("SubURL") int SubURL,
            @Query("SolutionFlag") int SolutionFlag,
            @Query("Solution") String Solution
    );
    @POST("insert_mcq.php")
    Call<McqQuestionModel> mcqQuestionNo(
            @Query("UserId") String UserId,
            @Query("Question") String Question,
            @Query("QuestionTitle") String QuestionTitle,
            @Query("Opt1") String Opt1,
            @Query("Opt2") String Opt2,
            @Query("Opt3") String Opt3,
            @Query("Opt4") String Opt4,
            @Query("SubURL") int SubURL,
            @Query("SolutionFlag") int SolutionFlag,
            @Query("Solution") String Solution
    );

    @POST("insert_mcq.php")
    Call<McqAskedQuestionModel> mcqAskedQuestion(
            @Query("UserId") String UserId,
            @Query("SubURL") int SubURL
    );


    @POST("insert_mcq.php")
    Call<McqQuestionSolutionModel> getMcqSolution(
            @Query("UserId") String UserId,
            @Query("SubURL") int SubURL,
            @Query("Solution") String Solution,
            @Query("QuestionId") String QuestionId
    );

    @POST("view-MCQ-data.php")
    Call<ArrayList<McqSolutionModel>> getMcqQuestionSolution(
            @Query("question_id") String question_id,
            @Query("user_id") String user_id,
            @Query("user_name") String user_name

            //user_id,user_name
    );

    @POST("view-solutions.php")
    @FormUrlEncoded
    Call<SIADSolutionMainModel> getTestSolution(
            @Field("paper_code") String paper_code,
            @Field("UserID") String UserID
    );

    @POST("fetch-exam-instruction.php")
    @FormUrlEncoded
    Call<InstructionsModel> getInstruction(
            @Field("PaperID") String PaperID
    );

    @POST("for_filter_value.php")
    Call<FacultyName> getFaculty(
            @Query("filter_type") long filter_type
    );


    @POST("live_class_availability.php")
    Call<LiveStatus> getLiveStatus(
            @Query("ORG_ID") String ORG_ID
    );


    @POST("get-order-id-api.php")
    @FormUrlEncoded
    Call<PaymentModel> fetchPaymentData(
            @Field("course_id") String course_id,
            @Field("user_id") String user_id,
            @Field("amount_org_id") String amount_org_id,
            @Field("org_id") String org_id
    );

    @POST("get-order-id-api.php")
    @FormUrlEncoded
    Call<PaymentModel> fetchPaymentDataPdf(
            @Field("course_id") String course_id,
            @Field("user_id") String user_id,
            @Field("amount_org_id") String amount_org_id,
            @Field("org_id") String org_id,
            @Field("type") String type
    );
    @POST("insert-exam-payment-initiated-ots.php")
    @FormUrlEncoded
    Call<TestSeriesPayment> fetchTestPaymentData(
            @Field("course_id") String course_id,
            @Field("user_id") String user_id,
            @Field("amount_org_id") String amount_org_id,
            @Field("org_id") String org_id,
            @Field("subscription_id") String subscription_id
    );

    @POST("fetch_payment_data.php")
    Call<ArrayList<TxnModel>> getPaymentData(
            @Query("USER_ID") String USER_ID,
            @Query("ORG_ID") String ORG_ID
    );


    @POST("insert_liveClass_chat.php")
    Call<LiveChatMessage> getLiveMessage(
            @Query("SubURL") int SubURL,
            @Query("UserId") String UserId,
            @Query("UserName") String UserName,
            @Query("LiveClassId") String LiveClassId,
            @Query("Message") String Message,
            @Query("DeviceId") String DeviceId
    );
    @POST("insert_liveClass_chat.php")
    Call<LiveMessage> getLiveMessageFetch(
            @Query("SubURL") int SubURL,
            @Query("UserId") String UserId,
            @Query("DeviceId") String DeviceId,
            @Query("LiveClassId") String LiveClassId
    );

}
