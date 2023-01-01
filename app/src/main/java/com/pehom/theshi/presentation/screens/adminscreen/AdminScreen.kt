package com.pehom.theshi.presentation.screens.adminscreen

import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pehom.theshi.R
import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.domain.model.VocabularyItemScheme
import com.pehom.theshi.domain.model.VocabularyTitle
import com.pehom.theshi.domain.model.VocabularyUploadToFs
import com.pehom.theshi.presentation.screens.components.DialogConfirmVocabularyUpload
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import org.apache.poi.hssf.usermodel.HSSFWorkbook


@Composable
fun AdminScreen(
    viewModel: MainViewModel
) {
    Log.d("ppp", "AdminScreen is on")
    val progressState = remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (progressState.value){
            CircularProgressIndicator(
              //  modifier = Modifier,
                color = Color.Yellow,
                strokeWidth = 2.dp
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .alpha(if (progressState.value) 0.3f else 1f),
         //   .background(if (progressState.value) Color.Gray else Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
       DocPicker(
           modifier = Modifier
               .fillMaxSize()
               .padding(bottom = 10.dp),
           viewModel.application,
           viewModel,
           progressState
       )
       }

}

@Composable
private fun DocPicker(
    modifier: Modifier = Modifier,
    application: Application,
    viewModel: MainViewModel,
    progressState: MutableState<Boolean>
){
    val TAG = "DocPicker"
    val context = LocalContext.current
    val xlsUri = remember{ mutableStateOf("") }
    val xlsTitle = remember {mutableStateOf("xls title")}
    val items = remember{ mutableStateListOf(VocabularyItemScheme("","","")) }
    val vcbUpload = remember { mutableStateOf(VocabularyUploadToFs(
        Vocabulary(VocabularyTitle(""), mutableListOf()),
        "Level none",
        0
    )) }
    val dialogState = remember { mutableStateOf(false) }
    val docPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null){
            vcbUpload.value = getVcbFromExcel( application, uri)
            xlsTitle.value = vcbUpload.value.vcb.title.value
            items.clear()
            items.addAll(vcbUpload.value.vcb.items)
        }
    }
    DialogConfirmVocabularyUpload(
        viewModel = viewModel,
        vcbUploadToFs = vcbUpload,
        _dialogState = dialogState,
        _progressState = progressState
    )
    Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                elevation = 5.dp
            ) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                    Text(xlsTitle.value, fontSize = 16.sp)
                }
            }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
                contentAlignment = Alignment.Center
            ){
                Text(text = Constants.ORIG)
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
                contentAlignment = Alignment.Center
                ){
                Text(text = Constants.TRANS)
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
                contentAlignment = Alignment.Center
                ){
                Text(text = Constants.IMG_URL)
            }
        }

            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(15f),
                contentAlignment = Alignment.Center
            ){
                LazyColumn(
                    modifier = Modifier.fillMaxSize()

                ) {
                    itemsIndexed(items){index, item ->
                        Row(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Box(modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .weight(1f),
                                contentAlignment = Alignment.Center
                                ){
                                Text(text ="${item.orig}" )
                            }
                            Box(modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .weight(1f),
                                contentAlignment = Alignment.Center
                            ){
                                Text(text ="${item.trans}" )
                            }
                            Box(modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .weight(1f),
                                contentAlignment = Alignment.Center
                            ){
                                Text(text ="${item.imgUrl}" )
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(2f),
                contentAlignment = Alignment.Center
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        onClick = {
                        try {
                            docPicker.launch(arrayOf("*/*"))

                        } catch (e: java.lang.Exception) {
                            Log.d("docPicker", "exception: ${e.message}")
                        }
                    }) {
                        Text(text = "Select document")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        onClick = {
                            viewModel.useCases.checkExistedVocabularyByTitleAndLevelFsUseCase.execute(
                                vcbUpload.value.vcb.title.value,
                                vcbUpload.value.level
                                ){
                                Log.d(TAG, "checkVocabularyByTitleAndLevel result = $it")
                                if (!it) {
                                    viewModel.useCases.uploadVocabularyToFsUseCase.execute(vcbUpload.value){
                                        if (it) {
                                            Toast.makeText(context, context.getString(R.string.vocabulary_loaded ), Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(context, context.getString(R.string.loading_failed ), Toast.LENGTH_SHORT).show()
                                        }
                                        Log.d(TAG, "vocabulary is uploaded successfully")
                                    }
                                } else {
                                    dialogState.value = true
                                }
                            }
                        }) {
                        Text("Add vcb to firestore")
                    }
                }
            }
        }
}

private fun getVcbFromExcel(
    application: Application,
    uri: Uri): VocabularyUploadToFs
{

    var vcbUpload = VocabularyUploadToFs(
        Vocabulary(VocabularyTitle(""), mutableListOf()),
        "Level none",
        0
    )

    try {
        val file = application.contentResolver.openInputStream(uri)
        val workbook = HSSFWorkbook(file)
        val sheet = workbook.getSheetAt(0)
        val vcbTitle = sheet.getRow(0).getCell(1).stringCellValue
        val level  = sheet.getRow(1).getCell(1).stringCellValue
        val price = sheet.getRow(2).getCell(1).numericCellValue.toInt()
        val vcb = Vocabulary(VocabularyTitle(vcbTitle), mutableListOf())

        for (i in 4..sheet.lastRowNum) {
            val row = sheet.getRow(i)
            if (row.physicalNumberOfCells == 3) {
                val orig = row.getCell(0).stringCellValue
                val trans = row.getCell(1).stringCellValue
                val imgUrl = row.getCell(2).stringCellValue
                vcb.items.add(VocabularyItemScheme(orig, trans, imgUrl))
            }
        }
        vcbUpload = VocabularyUploadToFs(vcb, level, price)
        file?.close()
    } catch (e: java.lang.Exception) {
        Log.d("getVcbFromExcel", "getting vcb from excel failed, Error: ${e.message}")
    }
    return vcbUpload
}


