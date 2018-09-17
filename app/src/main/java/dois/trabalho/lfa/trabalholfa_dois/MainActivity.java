package dois.trabalho.lfa.trabalholfa_dois;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private LinearLayout layoutEntrada;
    private EditText etPalavraEntra;
    private EditText etSaida;

    private Button btnVerificarInput;

    private EditText etVerbos;
    private EditText etTerminais;
    private EditText etRegras;
    private EditText etSimboloInicial;

    private Button btnValidar;

    private List<String> verbos = new ArrayList<>();
    private List<String> terminais = new ArrayList<>();
    private Map<String, String> regras = new HashMap<String, String>();
    private String simboloInicial = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutEntrada = (LinearLayout) findViewById(R.id.layoutEntradas);
        etPalavraEntra = (EditText) findViewById(R.id.etPalavraEntrada);
        etSaida = (EditText) findViewById(R.id.etSaida);

        btnVerificarInput = (Button) findViewById(R.id.btnVerificarInput);

        etVerbos = (EditText) findViewById(R.id.etVerbos);
        etTerminais = (EditText) findViewById(R.id.etTerminais);
        etRegras = (EditText) findViewById(R.id.etRegras);

        etSimboloInicial = (EditText) findViewById(R.id.etSimboloInicial);

        this.btnValidar = (Button) findViewById(R.id.btnValidar);

        this.btnValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etVerbos.getText().toString().trim().length() == 0) {
                    Toast.makeText(MainActivity.this, "Digite os verbos.", Toast.LENGTH_LONG).show();
                }else if (etVerbos.getText().toString().trim().charAt(etVerbos.getText().toString().trim().length()-1) == ',') {
                    Toast.makeText(MainActivity.this, "Verbos inválidos. Vírgula no final.", Toast.LENGTH_LONG).show();
                }else if (etTerminais.getText().toString().trim().length() == 0) {
                    Toast.makeText(MainActivity.this, "Digite os terminais.", Toast.LENGTH_LONG).show();
                }else if (etTerminais.getText().toString().trim().charAt(etTerminais.getText().toString().trim().length()-1) == ',') {
                    Toast.makeText(MainActivity.this, "Terminais inválidos. Vírgula no final.", Toast.LENGTH_LONG).show();
                }else if (etRegras.getText().toString().trim().length() == 0) {
                    Toast.makeText(MainActivity.this, "Digite as regras.", Toast.LENGTH_LONG).show();
                }else if (etRegras.getText().toString().trim().charAt(etRegras.getText().toString().trim().length()-1) == '|') {
                    Toast.makeText(MainActivity.this, "Regras inválidas.", Toast.LENGTH_LONG).show();
                }else if (etRegras.getText().toString().trim().charAt(etRegras.getText().toString().trim().length()-1) == '-') {
                    Toast.makeText(MainActivity.this, "Regras inválidas.", Toast.LENGTH_LONG).show();
                }else if (etSimboloInicial.getText().toString().trim().length() == 0) {
                    Toast.makeText(MainActivity.this, "Digite o símbolo inicial.", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this, "Válido!", Toast.LENGTH_LONG).show();
                    organizarPropriedades();
                    layoutEntrada.setVisibility(View.VISIBLE);
                }

            }
        });
        this.btnVerificarInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPalavraEntra.getText().toString().trim().length() == 0) {
                    Toast.makeText(MainActivity.this, "Digite algo no input.", Toast.LENGTH_LONG).show();
                }else {
                    checkInput();
                }
            }
        });

    }

    private void organizarPropriedades() {
        organizarVerbos(this.etVerbos.getText().toString().trim());
        organizarTerminais(this.etTerminais.getText().toString().trim());
        organizarRegras(this.etRegras.getText().toString().trim());

        this.simboloInicial = etSimboloInicial.getText().toString().trim();

        printAll();
    }

    private void organizarVerbos(String verbos) {

        for (int i=0; i<verbos.length(); i++) {
            if (verbos.charAt(i) != ',') {
                this.verbos.add(String.valueOf(verbos.charAt(i)));
            }
        }

    }

    private void organizarTerminais(String terminais) {

        for (int i=0; i<terminais.length(); i++) {
            if (terminais.charAt(i) != ',') {
                this.terminais.add(String.valueOf(terminais.charAt(i)));
            }
        }

    }

    private void organizarRegras(String regras) {

        String verboAtual = "";

        String itemRegraAtual = "";
        String itemRegraPosterior = "";

        boolean primeira = true;

        int auxCaminhoRegras = 0;
        for (int i=0; i<this.verbos.size(); i++) {
            verboAtual = this.verbos.get(i);
            String regrasDoVerbo = "";
            for (int j=auxCaminhoRegras; j<regras.length(); j++) {

                itemRegraAtual = String.valueOf(regras.charAt(j));

                if (j == regras.length()-1) {
                    itemRegraPosterior = "";
                }else {
                    itemRegraPosterior = String.valueOf(regras.charAt(j+1));
                }

                if (itemRegraAtual.equals(verboAtual) && itemRegraPosterior.equals("-")) {
                    continue;
                }else if (!(itemRegraAtual.equals(verboAtual)) && itemRegraPosterior.equals("-")) {
                    auxCaminhoRegras = j;
                    break;
                }else if (itemRegraAtual.equals("-")) {
                    continue;
                }else if (itemRegraAtual.equals("\n")) {
                    continue;
                }

                if (!itemRegraPosterior.equals("|") && !itemRegraPosterior.equals("\n")) {
//                    this.regras.put(verboAtual, itemRegraAtual+itemRegraPosterior);
                    regrasDoVerbo += itemRegraAtual+itemRegraPosterior;
                    j+=1;
                }else{
//                    this.regras.put(verboAtual, itemRegraAtual);
                    regrasDoVerbo += itemRegraAtual;
                }


            }

//            regrasDoVerbo = regrasDoVerbo.replace("||", "|");
            this.regras.put(verboAtual, regrasDoVerbo);

        }

    }

    private void printAll() {
        Log.i("VERBOS", this.verbos.toString());
        Log.i("TERMINAIS", this.terminais.toString());
        Log.i("REGRAS", "Regras");

        for (String key : regras.keySet()) {

            //Capturamos o valor a partir da chave
            String value = regras.get(key);
            System.out.println(key + " = " + value);
        }
    }


    private void checkInput() {

    }


}
