package com.pehom.theshi.domain.usecase

import com.pehom.theshi.domain.usecase.firestoreusecase.*
import com.pehom.theshi.domain.usecase.firestoreusecase.SetupMainViewModelFsUseCase
import com.pehom.theshi.domain.usecase.roomusecase.*

data class UseCases(
  //  val getAllVocabularyTitlesFsUseCase: GetAllVocabularyTitlesFsUseCase,
    val setTaskByVocabulary: SetTaskByVocabulary,
    val signInUseCase: SignInUseCase,
    val createFirestoreAccountUseCase: CreateFirestoreAccountUseCase,
    val readFirestoreUserInfoUseCase: ReadFirestoreUserInfoUseCase,
    val addUserTaskFsUseCase: AddUserTaskFsUseCase,
    val addStudentTaskFsUseCase: AddStudentTaskFsUseCase,
    val addVocabularyToWordbookFsUseCase: AddVocabularyToWordbookFsUseCase,
    val readWordbookFsUseCase: ReadWordbookFsUseCase,
    val cancelRequestAddFsUseCase: CancelRequestAddUseCase,
    val declineRequestAddUseCase: DeclineRequestAddUseCase,
    val acceptRequestAddUseCase: AcceptRequestAddUseCase,
    val readRequestsAddFsUseCase: ReadRequestsAddFsUseCase,
    val createRequestAddFsUseCase: CreateRequestAddFsUseCase,
    val getUserFsIdByPhoneFsUseCase: GetUserFsIdByPhoneFsUseCase,
    val signOutUseCase: SignOutUseCase,
    val addStudentFsUseCase: AddStudentFsUseCase,
    val readStudentsFsUseCase: ReadStudentsFsUseCase,
    val readStudentTasksFsUseCase: ReadStudentTasksFsUseCase,
    val setupMainViewModelFsUseCase: SetupMainViewModelFsUseCase,
    val setTaskProgressFsUseCase: SetTaskProgressFsUseCase,
    val setTaskIdFactoryFsUseCase: SetTaskIdFactoryFsUseCase,
    val updateLastTaskIdSfxFsUseCase: UpdateLastTaskIdSfxFsUseCase,
    val getAllVocabularyTitlesFsUseCase: GetAllVocabularyTitlesFsUseCase,
    val updateTaskFsUseCase: UpdateTaskFsUseCase,
    val addTaskRoomUseCase: AddTaskRoomUseCase,
    val addVocabularyToWordbookRoomUseCase: AddVocabularyToWordbookRoomUseCase,
    val addStudentRoomUseCase: AddStudentRoomUseCase,
    val readAllUserTasksFsUseCase: ReadAllUserTasksFsUseCase,
    val setCurrentTaskByTaskIdFsUseCase: SetCurrentTaskByTaskIdFsUseCase,
    val setTaskByIdRoomUseCase: SetTaskByIdRoomUseCase,
    val buyVocabularyWithLoadedItemsUseCase: BuyVocabularyWithLoadedItemsUseCase,
    val readVcbItemsByVcbDocRefFsUseCase: ReadVcbItemsByVcbDocRefFsUseCase,
    val readVcbTitleByFsDocRefPathFsUseCase: ReadVcbTitleByFsDocRefPathFsUseCase,
    val checkAvailableVocabularyByFsDocRefPathFsUseCase: CheckAvailableVocabularyByFsDocRefPathFsUseCase,
    val saveAvailableVocabularyRoomUseCase: SaveAvailableVocabularyRoomUseCase,
    val getWordbookSizeByUserFsId: GetWordbookSizeByUserFsId,
    val deleteTaskByIdFsUseCase: DeleteTaskByIdFsUseCase,
    val getVocabularyByFsDocRefRoomUseCase: GetVocabularyByFsDocRefRoomUseCase,
    val setAllWordsWordbookTaskUseCase: SetAllWordsWordbookTaskUseCase
)
