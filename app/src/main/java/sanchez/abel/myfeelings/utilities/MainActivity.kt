package sanchez.abel.myfeelings.utilities

import android.graphics.drawable.Icon
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.IconCompat
import org.json.JSONArray
import org.json.JSONException
import sanchez.abel.myfeelings.R

class MainActivity: AppCompatActivity() {
    var jsonFile: JSONFile? = null
    var veryHappy = 0.0F
    var happy = 0.0F
    var neutral = 0.0F
    var sad = 0.0F
    var verysad = 0.0F
    var data: Boolean = false
    var lista = ArrayList<Emociones>()

    fun fetchingData(){
        try {
            var json: String = jsonFile?.getData(this) ?: ""
            if(json != ""){
                this.data = true
                var jsonArray: JSONArray = JSONArray(json)

                this.lista = parseJson(jsonArray)

                for(i in lista){
                    when(i.nombre){
                        "Muy feliz" -> veryHappy = i.total
                        "Feliz" -> happy = i.total
                        "Neutral" -> neutral = i.total
                        "Triste" -> sad = i.total
                        "Muy triste"-> verysad = i.total
                    }
                }
            } else {
                this.data = false
            }
        } catch (exception: JSONException){
            exception.printStackTrace()
        }
    }

    val icon: ImageView = findViewById(R.id.icon)
    fun iconoMayoria(){
        if(happy>veryHappy && happy>neutral && happy>sad && happy>verysad){
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_happy))
        }
        if(happy>veryHappy && happy>neutral && happy>sad && happy>verysad){
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_happy))
        }
        if(happy>veryHappy && happy>neutral && happy>sad && happy>verysad){
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_happy))
        }
        if(happy>veryHappy && happy>neutral && happy>sad && happy>verysad){
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_happy))
        }
        if(happy>veryHappy && happy>neutral && happy>sad && happy>verysad){
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_happy))
        }
    }

    fun actualizarGrafica(){
        val total = veryHappy+happy+neutral+verysad+sad

        var pVH: Float = (veryHappy * 100 / total).toFloat()
        var pH: Float = (happy * 100 /total).toFloat()
        var pN: Float =(neutral*100 /total).toFloat()
        var pS: Float = (sad*100 / total).toFloat()
        var pVS: Float = (verysad * 100/ total).toFloat()

        Log.d("porcentajes", "very happy "+ pVH)
        Log.d("porcentajes", " happy "+ pH)
        Log.d("porcentajes", "neutral "+ pN)
        Log.d("porcentajes", "sad "+ pS)
        Log.d("porcentajes", "very sad "+ pVS)

        lista.clear()
        lista.add(Emociones("Muy feliz", pVH, R.color.mustard, veryHappy))
        lista.add(Emociones("Feliz", pH, R.color.orange, happy))
        lista.add(Emociones("Neutral", pN, R.color.greenie, neutral))
        lista.add(Emociones("Triste", pS, R.color.blue, sad))
        lista.add(Emociones("Muy triste", pVS, R.color.deepblue, verysad))

        val fondo = CustomCircleDrawable(this, lista)

        val graphVeryHappy: View = findViewById(R.id.graphVeryHappy)
        val graphHappy: View = findViewById(R.id.graphHappy)
        val graphNeutral: View = findViewById(R.id.graphNeutral)
        val graphSad: View = findViewById(R.id.graphSad)
        val graphVerySad: View = findViewById(R.id.graphVerySad)
        val graph: View = findViewById(R.id.graph)
        graphVeryHappy.background = CustomBarDrawable(this, Emociones("Muy feliz", pVH, R.color.mustard, veryHappy))
        graphHappy.background = CustomBarDrawable(this, Emociones("Feliz", pVH, R.color.orange, happy))
        graphNeutral.background = CustomBarDrawable(this, Emociones("Neutral", pVH, R.color.greenie, neutral))
        graphSad.background = CustomBarDrawable(this, Emociones("Triste", pVH, R.color.blue, sad))
        graphVerySad.background = CustomBarDrawable(this, Emociones("Muy Triste", pVH, R.color.deepblue, verysad))

        graph.background = fondo
    }

    fun parseJson (jsonArray: JSONArray): ArrayList<Emociones>{
        var lista = ArrayList<Emociones>()

        for(i in 0..jsonArray.length()){
            try {
                val nombre = jsonArray.getJSONObject(i).getString("nombre")
                val porcentaje = jsonArray.getJSONObject(i).getDouble("porcentaje").toFloat()
                val color = jsonArray.getJSONObject(i).getInt("color")
                val total =jsonArray.getJSONObject(i).getDouble("total").toFloat()
                var emocion = Emociones(nombre, porcentaje, color, total)
                lista.add(emocion)
            } catch (exception: JSONException){
                exception.printStackTrace()
            }
        }
        return lista
    }

    fun guardar(){}
}