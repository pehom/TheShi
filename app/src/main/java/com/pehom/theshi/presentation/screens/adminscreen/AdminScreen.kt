package com.pehom.theshi.presentation.screens.adminscreen

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.domain.model.VocabularyItemScheme
import com.pehom.theshi.domain.model.VocabularyTitle
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
//import org.apache.poi.hssf.usermodel.HSSFWorkbook
//import org.apache.poi.xssf.usermodel.XSSFWorkbook

//import org.apache.poi.xssf.usermodel.XSSFWorkbook

@Composable
fun AdminScreen(
    viewModel: MainViewModel
) {
    Log.d("ppp", "AdminScreen is on")
   /* Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
       DocPicker(
           modifier = Modifier
               .fillMaxSize()
               .padding(bottom = 10.dp),
           viewModel.application
       )
       }*/

}

//@Composable
//private fun DocPicker(
//    modifier: Modifier = Modifier,
//    application: Application
//){
//    val xlsUri = remember{ mutableStateOf("") }
//    val xlsTitle = remember {mutableStateOf("xlsx title")}
//    val items = remember{ mutableStateListOf(VocabularyItemScheme("","","")) }
//    val docPicker = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.OpenDocument()
//    ) { uri ->
//        if (uri != null){
//
//            xlsUri.value = uri.toString()
//            Log.d("docPicker", "doc's uri = $uri")
//            xlsTitle.value = uri.lastPathSegment?.split("/")?.last().toString()
//            val vcbTitle = xlsTitle.value.split(".")[0]
//            val path = uri.toString().split("//").last().toString()
//            Log.d("docPicker", "doc's path = $path")
//            val fileName = vcbTitle
//            val filePath = "com.android.providers.downloads.documents/document/raw"
//            val vcb = getVcbFromExcel( application, vcbTitle, uri)
//            items.clear()
//            items.addAll(vcb.items)
//        }
//    }
//    Column(
//            modifier = modifier,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 10.dp, vertical = 5.dp),
//                elevation = 5.dp
//            ) {
//                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
//                    Text(xlsTitle.value, fontSize = 16.sp)
//                }
//            }
//        Row(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Box(modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f),
//                contentAlignment = Alignment.Center
//            ){
//                Text(text = Constants.ORIG)
//            }
//            Box(modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f),
//                contentAlignment = Alignment.Center
//                ){
//                Text(text = Constants.TRANS)
//            }
//            Box(modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f),
//                contentAlignment = Alignment.Center
//                ){
//                Text(text = Constants.IMG_URL)
//            }
//        }
//
//            Box(modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight()
//                .weight(15f),
//                contentAlignment = Alignment.Center
//            ){
//                LazyColumn(
//                    modifier = Modifier.fillMaxSize()
//                ) {
//                    itemsIndexed(items){index, item ->
//                        Row(
//                            modifier = Modifier.fillMaxSize()
//                        ) {
//                            Box(modifier = Modifier
//                                .fillMaxHeight()
//                                .fillMaxWidth()
//                                .weight(1f)){
//                                Text(text ="${item.orig}" )
//                            }
//                            Box(modifier = Modifier
//                                .fillMaxHeight()
//                                .fillMaxWidth()
//                                .weight(1f)){
//                                Text(text ="${item.trans}" )
//                            }
//                            Box(modifier = Modifier
//                                .fillMaxHeight()
//                                .fillMaxWidth()
//                                .weight(1f)){
//                                Text(text ="${item.imgUrl}" )
//                            }
//                        }
//                        Spacer(modifier = Modifier.height(5.dp))
//                    }
//                }
//            }
//            Box(modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight()
//                .weight(1f),
//                contentAlignment = Alignment.Center
//            ){
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 15.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Button(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .weight(1f),
//                        onClick = {
//                        try {
//                            docPicker.launch(arrayOf("*/*"))
//
//                        } catch (e: java.lang.Exception) {
//                            Log.d("docPicker", "exception: ${e.message}")
//                        }
//                    }) {
//                        Text(text = "Select document")
//                    }
//                    Spacer(modifier = Modifier.width(30.dp))
//                    Button(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .weight(1f),
//                        onClick = { /*TODO*/ }) {
//                        Text("add vcb to firestore")
//                    }
//                }
//            }
//        }
//}

/*private fun getVcbFromExcel(
    application: Application,
    _vcbTitle: String,
    uri: Uri): Vocabulary
{
    val vcbTitle = VocabularyTitle(_vcbTitle)
    val vcb = Vocabulary(vcbTitle, mutableListOf())
    try {
        if (uri != null) {
            val file = application.contentResolver.openInputStream(uri)

            val workbook = XSSFWorkbook(file)
            val sheet = workbook.getSheetAt(0)
            sheet.rowIterator().forEach {
                if (it.physicalNumberOfCells == 3) {
                    val orig = it.getCell(0).stringCellValue
                    val trans = it.getCell(1).stringCellValue
                    val imgUrl = it.getCell(2).stringCellValue
                    vcb.items.add(VocabularyItemScheme(orig, trans, imgUrl))
                }
            }
        }
    } catch (e: java.lang.Exception) {
        Log.d("getVcbFromExcel", "getting vcb from excel failed, Error: ${e.message}")
    }
    return vcb
}*/

/*
private fun createWorkbook(): HSSFWorkbook {
    val xlWorkbook = HSSFWorkbook()
    val sheet = xlWorkbook.createSheet(Constants.NEW_VOCABULARY_XL)
    val row = sheet.createRow(0)

    row.createCell(0).setCellValue(Constants.ORIG)
    row.createCell(1).setCellValue(Constants.TRANS)
    row.createCell(2).setCellValue(Constants.IMG_URL)

    return xlWorkbook
}*/
