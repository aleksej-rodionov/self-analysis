package space.rodionov.selfanalysis.feature_self_analysis.data.repository

import android.util.Log
import kotlinx.coroutines.flow.*
import space.rodionov.selfanalysis.feature_self_analysis.domain.model.Analysis
import space.rodionov.selfanalysis.feature_self_analysis.domain.repository.AnalysisRepo
import space.rodionov.selfanalysis.util.Constants.EMPTY
import space.rodionov.selfanalysis.util.Constants.TAG_DEBUG

class AnalysisRepoFakeImpl: AnalysisRepo {



    private val fakeList = listOf(
        Analysis(
            situation = "Now these are the names of the children of Israel, which came into Egypt; every man and his household came with Jacob.",
            emotions = "Reuben, Simeon, Levi, and Judah",
            feelings = "Issachar, Zebulun, and Benjamin",
            inTheBody = "Dan, and Naphtali, Gad, and Asher.",
            wantedToDo = "And all the souls that came out of the loins of Jacob were seventy souls: for Joseph was in Egypt already.",
            whatDoesTheFeelingMean = "And Joseph died, and all his brethren, and all that generation.",
            thoughts = "Now there arose up a new king over Egypt, which knew not Joseph.",
            fears = "And he said unto his people, Behold, the people of the children of Israel are more and mightier than we:",
            askingFromHP = "Come on, let us deal wisely with them; lest they multiply, and it come to pass, that, when there falleth out any war, they join also unto our enemies, and fight against us, and so get them up out of the land.",
            innerCritic = "Therefore they did set over them taskmasters to afflict them with their burdens. And they built for Pharaoh treasure cities, Pithom and Raamses.",
            lovingParent = "But the more they afflicted them, the more they multiplied and grew. And they were grieved because of the children of Israel.",
            date = "03.02.1991",
            id = 0,
        ),
        Analysis(
            situation = "And the Egyptians made the children of Israel to serve with rigour:",
            emotions = "And they made their lives bitter with hard bondage, in morter, and in brick, and in all manner of service in the field: all their service, wherein they made them serve, was with rigour.",
            feelings = "And the king of Egypt spake to the Hebrew midwives, of which the name of the one was Shiphrah, and the name of the other Puah:",
            inTheBody = "And he said, When ye do the office of a midwife to the Hebrew women, and see them upon the stools; if it be a son, then ye shall kill him: but if it be a daughter, then she shall live.",
            wantedToDo = "But the midwives feared God, and did not as the king of Egypt commanded them, but saved the men children alive.",
            whatDoesTheFeelingMean = "And the king of Egypt called for the midwives, and said unto them, Why have ye done this thing, and have saved the men children alive?",
            thoughts = "And the midwives said unto Pharaoh, Because the Hebrew women are not as the Egyptian women; for they are lively, and are delivered ere the midwives come in unto them.",
            fears = "Therefore God dealt well with the midwives: and the people multiplied, and waxed very mighty.",
            askingFromHP = "And it came to pass, because the midwives feared God, that he made them houses.",
            innerCritic = "And Pharaoh charged all his people, saying, Every son that is born ye shall cast into the river, and every daughter ye shall save alive.",
            lovingParent = "And there went a man of the house of Levi, and took to wife a daughter of Levi.",
            date = "04.02.1991",
            id = 1,
        ),
        Analysis(
            situation = "And the woman conceived, and bare a son: and when she saw him that he was a goodly child, she hid him three months.",
            emotions = "And when she could not longer hide him, she took for him an ark of bulrushes, and daubed it with slime and with pitch, and put the child therein; and she laid it in the flags by the river's brink.",
            feelings = "And his sister stood afar off, to wit what would be done to him.",
            inTheBody = "And the daughter of Pharaoh came down to wash herself at the river; and her maidens walked along by the river's side; and when she saw the ark among the flags, she sent her maid to fetch it.",
            wantedToDo = "And when she had opened it, she saw the child: and, behold, the babe wept. And she had compassion on him, and said, This is one of the Hebrews' children.",
            whatDoesTheFeelingMean = "Then said his sister to Pharaoh's daughter, Shall I go and call to thee a nurse of the Hebrew women, that she may nurse the child for thee?",
            thoughts = "And Pharaoh's daughter said to her, Go. And the maid went and called the child's mother.",
            fears = "And Pharaoh's daughter said unto her, Take this child away, and nurse it for me, and I will give thee thy wages. And the woman took the child, and nursed it.",
            askingFromHP = "And the child grew, and she brought him unto Pharaoh's daughter, and he became her son. And she called his name Moses: and she said, Because I drew him out of the water.",
            innerCritic = "And it came to pass in those days, when Moses was grown, that he went out unto his brethren, and looked on their burdens: and he spied an Egyptian smiting an Hebrew, one of his brethren.",
            lovingParent = "And he looked this way and that way, and when he saw that there was no man, he slew the Egyptian, and hid him in the sand.",
            date = "05.02.1991",
            id = 2,
        ),
    )

    override suspend fun deleteAnalysis(analysis: Analysis) {
        Log.d(TAG_DEBUG, "RepoFake.deleteAnalysis: CALLED")
    }

    override suspend fun updateAnalysis(analysis: Analysis) {
        Log.d(TAG_DEBUG, "RepoFake.updateAnalysis: CALLED")
    }

    override suspend fun insertAnalysis(analysis: Analysis) {
        Log.d(TAG_DEBUG, "RepoFake.insertAnalysis: CALLED")
    }

    override suspend fun deleteAll() {
        Log.d(TAG_DEBUG, "RepoFake.deleteAll: CALLED")
    }

    override fun getAllAnalysis(): Flow<List<Analysis>> = flow {
        Log.d(TAG_DEBUG, "getAllAnalysis: flowcollector listsize = ${fakeList.size}")
        emit(fakeList)
    }

    override fun getAnalysisBy(searchQuery: String?, emotionFilter: String?): Flow<MutableList<Analysis>> = flow {
//        emit(fakeList.filter {
//           (it.emotions.contains(emotionFilter) || it.feelings.contains(emotionFilter)) ||
//                   (it.situation.contains(searchQuery) ||
//                           it.emotions.contains(searchQuery) ||
//                           it.feelings.contains(searchQuery) ||
//                           it.inTheBody.contains(searchQuery) ||
//                           it.wantedToDo?.contains(searchQuery) == true ||
//                           it.whatDoesTheFeelingMean?.contains(searchQuery) == true ||
//                           it.thoughts?.contains(searchQuery) == true ||
//                           it.fears?.contains(searchQuery) == true ||
//                           it.askingFromHP?.contains(searchQuery) == true ||
//                           it.innerCritic?.contains(searchQuery) == true ||
//                           it.lovingParent?.contains(searchQuery) == true ||
//                           it.date.contains(searchQuery))
//       })

        emit(fakeList.filter {
            Log.d(TAG_DEBUG, "getAnalysisBy: flowcollector listsize = ${fakeList.size}")
            (it.emotions.contains(emotionFilter ?: EMPTY) || it.feelings.contains(emotionFilter ?: EMPTY)) ||
                    (it.situation.contains(searchQuery ?: EMPTY) ||
                            it.emotions.contains(searchQuery ?: EMPTY) ||
                            it.feelings.contains(searchQuery ?: EMPTY) ||
                            it.inTheBody.contains(searchQuery ?: EMPTY) ||
                            it.wantedToDo?.contains(searchQuery ?: EMPTY) == true ||
                            it.whatDoesTheFeelingMean?.contains(searchQuery ?: EMPTY) == true ||
                            it.thoughts?.contains(searchQuery ?: EMPTY) == true ||
                            it.fears?.contains(searchQuery ?: EMPTY) == true ||
                            it.askingFromHP?.contains(searchQuery ?: EMPTY) == true ||
                            it.innerCritic?.contains(searchQuery ?: EMPTY) == true ||
                            it.lovingParent?.contains(searchQuery ?: EMPTY) == true ||
                            it.date.contains(searchQuery ?: EMPTY))
        }.toMutableList())
    }
}