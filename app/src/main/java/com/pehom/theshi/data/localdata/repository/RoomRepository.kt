package com.pehom.theshi.data.localdata.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.pehom.theshi.data.localdata.DatabaseRepository
import com.pehom.theshi.data.localdata.approomdatabase.*

class RoomRepository(
    private val taskRoomDao: TaskRoomDao,
    private val wordbookDao: WordbookDao,
    private val vocabularyRoomDao: AvailableVocabularyRoomDao,
    private val studentDao: StudentDao,
    private val availableWordsRoomDao: AvailableWordsRoomDao,
    private val mentorRoomDao: MentorRoomDao,
    private val userRoomDao: UserRoomDao
    ) : DatabaseRepository {

    override val readAllWordbookRoomItems: LiveData<List<WordbookRoomItem>>
        get() = wordbookDao.getAllWordbookItems()

    override suspend fun createWordbookRoomItem(wordbookItem: WordbookRoomItem, onSuccess: () -> Unit) {
        wordbookDao.addWordbookItem(wordbookItem)
        onSuccess()
    }

    override suspend fun updateWordbookRoomItem(wordbookItem: WordbookRoomItem, onSuccess: () -> Unit) {
        wordbookDao.updateWordbookItem(wordbookItem)
        onSuccess()
    }

    override suspend fun deleteWordbookRoomItem(wordbookItem: WordbookRoomItem, onSuccess: () -> Unit) {
        wordbookDao.deleteWordbookItem(wordbookItem)
        onSuccess()
    }

    override suspend fun readWordbookRoomItemByVcbDocRefPath(
        vcbDocRefPath: String,
        onSuccess: (WordbookRoomItem?) -> Unit
    ) {
        onSuccess(wordbookDao.getWordbookItemByVcbDocRefPath(vcbDocRefPath))
       // return wordbookDao.getWordbookItemByVcbDocRefPath(vcbDocRefPath)
    }

    override suspend fun readWordbookRoomItemsByUserFsId(_userFsId: String): List<WordbookRoomItem> {
        return wordbookDao.getWordbookItemsByUserFsID(_userFsId)
    }

    override val readAllTaskRoomItems: LiveData<List<TaskRoomItem>>
        get() = taskRoomDao.getAllTaskRoomItems()

    override  fun readTaskRoomItemsByUserFsId(
        userFsId: String
    ): LiveData<List<TaskRoomItem>> {
        return taskRoomDao.getTaskRoomItemsByFsId(userFsId)
    }

    override suspend fun readTaskRoomItemIdsByUserFsId(userFsId: String): List<String> {
        return taskRoomDao.getTaskRoomItemIdsByUserFsId(userFsId)
    }

    override suspend fun readTaskRoomItemById(taskId: String, onSuccess: (TaskRoomItem?) -> Unit) {
        onSuccess(taskRoomDao.getTaskRoomItemById(taskId))
    }

    override suspend fun getTaskRoomItemsCountByUserFsId(userFsId: String): Int {
        return taskRoomDao.getTaskRoomItemsCountByUserFsId(userFsId)
    }

    override suspend fun createTaskRoomItem(taskRoom: TaskRoomItem, onSuccess: () -> Unit) {
        taskRoomDao.addTaskRoomItem(taskRoom)
        onSuccess()
    }

    override suspend fun updateTaskRoomItem(taskRoom: TaskRoomItem, onSuccess: () -> Unit) {
        taskRoomDao.updateTaskRoomItem(taskRoom)
        onSuccess()
    }

    override suspend fun deleteTaskRoomItem(taskRoom: TaskRoomItem, onSuccess: () -> Unit) {
        taskRoomDao.deleteTaskRoomItem(taskRoom)
        onSuccess()
    }

    override val readAllAvailableVocabularyRoomItems: LiveData<List<AvailableVocabularyRoomItem>>
        get() = vocabularyRoomDao.getAllVocabularyRoomItems()

    override suspend fun readAvailableVocabularyRoomItemByVcbDocRefPath(
       vcbDocRefPath: String,
       userFsId: String,
       onSuccess: (AvailableVocabularyRoomItem?) -> Unit
    ) {
        onSuccess(vocabularyRoomDao.getAvailableVocabularyRoomItemByVcbDocRefPath(vcbDocRefPath, userFsId))
    }

    override fun readAvailableVocabularyRoomItemsByUserFsId(userFsId: String): LiveData<List<AvailableVocabularyRoomItem>> {
        return vocabularyRoomDao.readAvailableVocabularyItemsByUserFsId(userFsId)
    }


    override suspend fun createAvailableVocabularyRoomItem(
        vocabularyRoomItem: AvailableVocabularyRoomItem,
        onSuccess: () -> Unit
    ) {
        vocabularyRoomDao.addVocabularyRoomItem(vocabularyRoomItem)
        onSuccess()
    }

    override suspend fun updateAvailableVocabularyRoomItem(
        vocabularyRoomItem: AvailableVocabularyRoomItem,
        onSuccess: () -> Unit
    ) {
        vocabularyRoomDao.updateVocabularyRoomItem(vocabularyRoomItem)
        onSuccess()
    }

    override suspend fun deleteAvailableVocabularyRoomItem(
        vocabularyRoomItem: AvailableVocabularyRoomItem,
        onSuccess: () -> Unit
    ) {
        vocabularyRoomDao.deleteVocabularyRoomItem(vocabularyRoomItem)
        onSuccess()
    }

    override val readAllStudentRoomItems: LiveData<List<StudentRoomItem>>
        get() = studentDao.getAllStudentRoomItems()

    override fun readStudentRoomItemsByMentorId(mentorId: String): LiveData<List<StudentRoomItem>> {
        return studentDao.getStudentRoomItemsByMentorId(mentorId)
    }

    override fun readStudentRoomItemByFsId(studentFsId: String/*, onSuccess: (StudentRoomItem) -> Unit*/): LiveData<StudentRoomItem> {
        // onSuccess(studentDao.getStudentRoomItemByFsId(studentFsId))
        return studentDao.getStudentRoomItemByFsId(studentFsId)
    }

    override suspend fun createStudentRoomItem(
        studentRoomItem: StudentRoomItem,
        onSuccess: () -> Unit
    ) {
        studentDao.addStudentRoomItem(studentRoomItem)
        onSuccess()
    }

    override suspend fun updateStudentRoomItem(
        studentRoomItem: StudentRoomItem,
        onSuccess: () -> Unit
    ) {
        studentDao.updateStudentRoomItem(studentRoomItem)
        onSuccess()
    }

    override suspend fun deleteStudentRoomItem(
        studentRoomItem: StudentRoomItem,
        onSuccess: () -> Unit
    ) {
        studentDao.deleteStudentRoomItem(studentRoomItem)
        onSuccess()
    }

    override val readAllAvailableWordsRoomItem: LiveData<List<AvailableWordsRoomItem>>
        get() = availableWordsRoomDao.getAvailableWordsRoomItems()

    override suspend fun addAvailableWordsRoomItem(availableWordsRoomItem: AvailableWordsRoomItem) {
        availableWordsRoomDao.addAvailableWordsRoomItem(availableWordsRoomItem)
    }

    override suspend fun readAvailableWordsRoomItemsByVcbDocRefPath(vcbDocRefPath: String, userFsId: String, onSuccess: (List<AvailableWordsRoomItem>) -> Unit) {
        onSuccess(availableWordsRoomDao.getAvailableWordsRoomItemsByVcbDocRefPath(vcbDocRefPath, userFsId))
    }

    override fun readAvailableWordsRoomItemsByUserFsIdAsLiveData(userFsId: String): LiveData<List<AvailableWordsRoomItem>> {
        return availableWordsRoomDao.getAvailableWordsRoomItemsByUserFsIdAsLiveData(userFsId)
    }

    override suspend fun readAvailableWordsRoomItemsByUserFsIdAsList(userFsId: String, onSuccess: (List<AvailableWordsRoomItem>) -> Unit)  {
        onSuccess(availableWordsRoomDao.getAvailableWordsRoomItemsByUserFsIdAsList(userFsId))
    }

    override suspend fun getAvailableVocabularyRoomItemSizeByVcbDocRefPath(vcbDocRefPath: String): Int {
        return availableWordsRoomDao.getAvailableVocabularyRoomItemSizeByVcbDocRefPath(vcbDocRefPath)
    }

    override suspend fun getAvailableWordsRoomItemsCountByUserFsIdAndVcbDocRefPath(
        userFsId: String,
        vcbDocRefPath: String,

    ): Int {
        return availableWordsRoomDao.getAvailableWordsRoomItemsCountByUserFsIdAndVcbDocRefPath(userFsId,vcbDocRefPath)
    }

    override suspend fun updateAvailableWordsRoomItem(availableWordsRoomItem: AvailableWordsRoomItem) {
        availableWordsRoomDao.updateAvailableWordsRoomItem(availableWordsRoomItem)
    }

    override suspend fun deleteAvailableWordsRoomItem(availableWordsRoomItem: AvailableWordsRoomItem) {
        availableWordsRoomDao.deleteAvailableWordsRoomItem(availableWordsRoomItem)
    }

    override val readAllMentorRoomItems: LiveData<List<MentorRoomItem>>
        get() = mentorRoomDao.readAllMentorRoomItems()

    override suspend fun addMentorRoomItem(mentorRoomItem: MentorRoomItem) {
        Log.d("addMentor", "addMentorRoomItem invoked, mentor = $mentorRoomItem")

        mentorRoomDao.addMentorRoomItem(mentorRoomItem)
    }

    override suspend fun readMentorRoomItemByMentorFsId(mentorFsId: String, onSuccess: (MentorRoomItem?) -> Unit){
       onSuccess(mentorRoomDao.readMentorRoomItemByMentorFsId(mentorFsId))
    }

    override suspend fun getMentorRoomItemsCountByUserFsId(
        userFsId: String,
        onSuccess: (Int) -> Unit
    ) {
        onSuccess(mentorRoomDao.getMentorRoomItemsCountByUserFsId(userFsId))
    }

    override fun readMentorRoomItemsByUserFsIdAsLiveData(userFsId: String): LiveData<List<MentorRoomItem>> {
        return mentorRoomDao.readMentorRoomItemsByUserFsIdAsLiveData(userFsId)
    }

    override suspend fun readMentorRoomItemsByUserFsIdAsList(userFsId: String): List<MentorRoomItem> {
        return mentorRoomDao.readMentorRoomItemsByUserFsIdAsList(userFsId)
    }

    override suspend fun updateMentorRoomItem(mentorRoomItem: MentorRoomItem) {
        mentorRoomDao.updateMentorRoomItem(mentorRoomItem)
    }

    override suspend fun deleteMentorRoomItem(mentorRoomItem: MentorRoomItem) {
        mentorRoomDao.deleteMentorRoomItem(mentorRoomItem)
    }

    override suspend fun addUserRoomItem(userRoomItem: UserRoomItem) {
        userRoomDao.addUserRoomItem(userRoomItem)
    }

    override suspend fun readUserRoomItemByUserFsId(userFsId: String): UserRoomItem? {
        return userRoomDao.getUserRoomItemByFsId(userFsId)
    }

    override suspend fun readUserRoomItemByEmailAndPassword(
        email: String,
        password: String
    ): UserRoomItem? {
        return userRoomDao.getUserRoomItemByEmailAndPassword(email, password)
    }

    override suspend fun updateUserRoomItem(userRoomItem: UserRoomItem) {
        userRoomDao.updateUserRoomItem(userRoomItem)
    }

    override suspend fun deleteUserRoomItem(userRoomItem: UserRoomItem) {
        userRoomDao.deleteUserRoomItem(userRoomItem)
    }
}