package com.pehom.theshi.data.localdata

import androidx.lifecycle.LiveData
import com.pehom.theshi.data.localdata.approomdatabase.*

interface DatabaseRepository {

    val readAllWordbookRoomItems: LiveData<List<WordbookRoomItem>>

    suspend fun createWordbookRoomItem(wordbookItem: WordbookRoomItem, onSuccess: () -> Unit)

    suspend fun updateWordbookRoomItem(wordbookItem: WordbookRoomItem, onSuccess: () -> Unit)

    suspend fun deleteWordbookRoomItem(wordbookItem: WordbookRoomItem, onSuccess: () -> Unit)

    suspend fun readWordbookRoomItemByVcbDocRefPath(vcbDocRefPath: String, onSuccess: (WordbookRoomItem?) -> Unit)

    suspend fun readWordbookRoomItemsByUserFsId(_userFsId: String): List<WordbookRoomItem>


    val readAllTaskRoomItems: LiveData<List<TaskRoomItem>>

    fun readTaskRoomItemsByUserFsId(userFsId: String): LiveData<List<TaskRoomItem>>

    suspend fun readTaskRoomItemIdsByUserFsId(userFsId: String): List<String>

    suspend fun readTaskRoomItemById(taskId: String, onSuccess: (TaskRoomItem?) -> Unit)

    suspend fun getTaskRoomItemsCountByUserFsId(userFsId: String): Int

    suspend fun createTaskRoomItem(taskRoom: TaskRoomItem, onSuccess: () -> Unit)

    suspend fun updateTaskRoomItem(taskRoom: TaskRoomItem, onSuccess: () -> Unit)

    suspend fun deleteTaskRoomItem(taskRoom: TaskRoomItem, onSuccess: () -> Unit)

    val readAllAvailableVocabularyRoomItems: LiveData<List<AvailableVocabularyRoomItem>>

    suspend fun readAvailableVocabularyRoomItemByVcbDocRefPath(vcbDocRefPath: String, userFsId: String, onSuccess: (AvailableVocabularyRoomItem?) -> Unit)

    fun readAvailableVocabularyRoomItemsByUserFsId(userFsId: String): LiveData<List<AvailableVocabularyRoomItem>>

    suspend fun createAvailableVocabularyRoomItem(vocabularyRoomItem: AvailableVocabularyRoomItem, onSuccess: () -> Unit)

    suspend fun updateAvailableVocabularyRoomItem(vocabularyRoomItem: AvailableVocabularyRoomItem, onSuccess: () -> Unit)

    suspend fun deleteAvailableVocabularyRoomItem(vocabularyRoomItem: AvailableVocabularyRoomItem, onSuccess: () -> Unit)

    val readAllStudentRoomItems: LiveData<List<StudentRoomItem>>

    fun readStudentRoomItemsByMentorId(mentorId: String): LiveData<List<StudentRoomItem>>

    fun readStudentRoomItemByFsId(studentFsId: String,/* onSuccess: (StudentRoomItem) -> Unit*/): LiveData<StudentRoomItem>

    suspend fun createStudentRoomItem(studentRoomItem: StudentRoomItem, onSuccess: () -> Unit)

    suspend fun updateStudentRoomItem(studentRoomItem: StudentRoomItem, onSuccess: () -> Unit)

    suspend fun deleteStudentRoomItem(studentRoomItem: StudentRoomItem, onSuccess: () -> Unit)

    val readAllAvailableWordsRoomItem: LiveData<List<AvailableWordsRoomItem>>

    suspend fun addAvailableWordsRoomItem(availableWordsRoomItem: AvailableWordsRoomItem)

    suspend fun readAvailableWordsRoomItemsByVcbDocRefPath(vcbDocRefPath: String,userFsId: String,onSuccess: (List<AvailableWordsRoomItem>) -> Unit)

    fun readAvailableWordsRoomItemsByUserFsIdAsLiveData(userFsId: String): LiveData<List<AvailableWordsRoomItem>>

    suspend fun readAvailableWordsRoomItemsByUserFsIdAsList(userFsId: String, onSuccess: (List<AvailableWordsRoomItem>) -> Unit)

    suspend fun getAvailableVocabularyRoomItemSizeByVcbDocRefPath(vcbDocRefPath: String): Int

    suspend fun getAvailableWordsRoomItemsCountByUserFsIdAndVcbDocRefPath(userFsId: String, vcbDocRefPath: String): Int

    suspend fun updateAvailableWordsRoomItem(availableWordsRoomItem: AvailableWordsRoomItem)

    suspend fun deleteAvailableWordsRoomItem(availableWordsRoomItem: AvailableWordsRoomItem)

    val readAllMentorRoomItems: LiveData<List<MentorRoomItem>>

    suspend fun addMentorRoomItem(mentorRoomItem: MentorRoomItem)

    suspend fun readMentorRoomItemByMentorFsId(mentorFsId: String, onSuccess: (MentorRoomItem?) -> Unit)

    suspend fun getMentorRoomItemsCountByUserFsId (userFsId: String, onSuccess: (Int) -> Unit)

    fun readMentorRoomItemsByUserFsIdAsLiveData(userFsId: String): LiveData<List<MentorRoomItem>>

    suspend fun readMentorRoomItemsByUserFsIdAsList(userFsId: String): List<MentorRoomItem>

    suspend fun updateMentorRoomItem(mentorRoomItem: MentorRoomItem)

    suspend fun deleteMentorRoomItem(mentorRoomItem: MentorRoomItem)

    suspend fun addUserRoomItem(userRoomItem: UserRoomItem)

    suspend fun readUserRoomItemByUserFsId(userFsId: String): UserRoomItem?

    suspend fun readUserRoomItemByEmailAndPassword(email: String, password: String): UserRoomItem?

    suspend fun updateUserRoomItem(userRoomItem: UserRoomItem)

    suspend fun deleteUserRoomItem(userRoomItem: UserRoomItem)


}