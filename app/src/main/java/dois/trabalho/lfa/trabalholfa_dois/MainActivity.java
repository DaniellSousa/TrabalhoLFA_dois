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
                    btnVerificarInput.setVisibility(View.VISIBLE);
                }

            }
        });
        this.btnVerificarInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPalavraEntra.getText().toString().trim().length() == 0) {
                    Toast.makeText(MainActivity.this, "Digite algo no input.", Toast.LENGTH_LONG).show();
                }else {
                    checkInput(etPalavraEntra.getText().toString().trim());
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


    private void checkInput(String input) {

        boolean inputValido = true;
        boolean sair1 = false;
        boolean sair2 = false;
        boolean sair3 = false;

        boolean temQueVoltar = false;
        boolean temQueParar = false;

        int countNaoTem = 0;
        for (int a=0; a<input.length(); a++) {

            countNaoTem = 0;
            for (int b=0; b<this.terminais.size(); b++) {
                if (!String.valueOf(input.charAt(a)).equals(this.terminais.get(b))) {
                    countNaoTem += 1;
                }
            }

            if (countNaoTem == (this.terminais.size())) {
                inputValido = false;
                break;
            }
        }

        if (!inputValido) {
            this.etSaida.setText("N");
            return;
        }

        // Verificação de strings consecutivas no verbo.
        for (String verbo: regras.keySet()) {

//            for (int a=0; a<regras.get(verbo).length(); ) {
//
//            }

            if (itemsOutroVerboContainsSequenciaAndNotInInput(regras.get(verbo), input)) {
                this.etSaida.setText("N");
                return;
            }

        }



        for (int i =0; i< input.length(); i++) {

            for (String verbo : regras.keySet()) {

                for (int z=0; z<regras.get(verbo).length(); z++) {
                    if (input.charAt(i) == regras.get(verbo).charAt(z)) {
                        inputValido = true;
                        sair3 = true;
                    }else if (!verbo.equals(String.valueOf(regras.get(verbo).charAt(z))) && isAnyVerb(String.valueOf(regras.get(verbo).charAt(z)))) {
                        // Navegar nos itens desse outro verbo isAnyVerb(String.valueOf(input.charAt(i))). Verificar se tem algum terminal em algum item desse outro verbo.

                        String itemsOutroVerbo = getItemsFromVerb(String.valueOf(input.charAt(i)));

                        // Verificação de strings consecutivas no verbo.

                        if (itemsOutroVerboContainsSequenciaAndNotInInput(itemsOutroVerbo, input)) {
                            sair2 = true;
                            sair1 = true;
                            inputValido = false;
                            break;
                        }

                        boolean sair4 = false;

                        for (int w=0; w<itemsOutroVerbo.length(); w++) {
                            if (input.charAt(i) == itemsOutroVerbo.charAt(w)) {
                                inputValido = true;
                                sair4 = true;
                                break;
                            }
                        }

                        if (sair4) {
                            break;
                        }

                    }

                    if (sair3) {
                        break;
                    }
                }


                if (sair2) {
                    break;
                }

            }


            if (sair1) {
                break;
            }

        }

        if (inputValido) {
            etSaida.setText("S");
        }else {
            etSaida.setText("N");
        }
    }

    private boolean itemsOutroVerboContainsSequenciaAndNotInInput(String items, String input) {
        boolean valid = false;

        boolean isSequencia = false;

        String sequencia = "";
        for (int i=0; i<items.length(); i++) {

            if (inputIsDiferenteDeVerbo(String.valueOf(items.charAt(i)))
                    && !String.valueOf(items.charAt(i)).equals("|") ) {
                sequencia += String.valueOf(items.charAt(i));
                isSequencia = true;
            }else {
                sequencia = "";

            }

            if (isSequencia && !sequencia.equals("")) {

                if (!input.contains(sequencia) && !sequenciaIsTerminalIsolado(sequencia)) {
                    valid = true;
                    break;
                }
            }

        }

        return valid;
    }

    private boolean sequenciaIsTerminalIsolado(String in) { // Verificar se a sequncia é um terminal isolado ou um sequencia de terminais
        boolean is = false;

        if (in.length() == 1) {
            is = true;
        }else {
            is = false;
        }

        return is;
    }

    private boolean inputIsDiferenteDeVerbo(String in) {
        boolean valid = false;

        int count = 0;
        for (int i=0; i<verbos.size(); i++) {

            if (!verbos.get(i).equals(in)) {
                count += 1;
            }

        }

        if (count == verbos.size()) {
            valid = true;
        }else {
            valid = false;
        }

        return valid;
    }

    private String getItemsFromVerb(String verb) {
        String items = "";

        for (String key : regras.keySet()) {
            if (key.equals(verb)) {
                items = regras.get(key);
                break;
            }
        }

        return items;
    }

    private boolean isAnyVerb(String item) {
        boolean is = false;

        for (int i=0; i<this.verbos.size(); i++) {

            if (item.equals(this.verbos.get(i))) {
                is = true;
                break;
            }
        }

        return is;
    }


    private String getPrimeiroVerboEncontrado(String valuesInVerb) {
        String r = "";
        boolean sair = false;
        for (int i=0; i<valuesInVerb.length(); i++) {
            for (int j=0; j<this.verbos.size(); j++) {

                if (this.verbos.get(j).equals(valuesInVerb.charAt(i))) {
                    sair = true;
                    r = String.valueOf(valuesInVerb.charAt(i));
                    break;
                }

            }

            if (sair) {
                break;
            }

        }

        return r;
    }


}
