package com.example.appcofre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtServicoProg;
    EditText edtNomeProg;
    EditText edtUsuarioProg;
    EditText edtSenhaProg;
    int quantidadeRegistros;
    int registroAtual;
    int idCredencialAtual;

    boolean editControl = true;

    private GestureDetectorCompat mGestureDetector;

    credencialModel credencial = new credencialModel();

    bdModel bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtServicoProg = (TextView) findViewById(R.id.txtServico);
        edtNomeProg = (EditText) findViewById(R.id.edtServico);
        edtUsuarioProg = (EditText) findViewById(R.id.edtUsuario);
        edtSenhaProg = (EditText) findViewById(R.id.edtSenha);

        mGestureDetector = new GestureDetectorCompat(this, new GestureListener());

        disableEditText();

        carregarRegistroZero();
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public void onLongPress(MotionEvent e) {
            if(editControl) {
                credencial.setId(idCredencialAtual);
                credencial.setNome(edtNomeProg.getText().toString());
                credencial.setUsuario(edtUsuarioProg.getText().toString());
                credencial.setSenha(edtSenhaProg.getText().toString());
                bd = new bdModel(getApplicationContext());
                bd.delete(bd.getTabela(), credencial);
                limpar();
                carregarRegistroZero();
                super.onLongPress(e);
                Toast.makeText(MainActivity.this, "Deletado", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if(editControl) {
                credencial.setId(idCredencialAtual);
                credencial.setNome(edtNomeProg.getText().toString());
                credencial.setUsuario(edtUsuarioProg.getText().toString());
                credencial.setSenha(edtSenhaProg.getText().toString());
                bd = new bdModel(getApplicationContext());
                bd.update(bd.getTabela(), credencial);
                carregarRegistroZero();
                Toast.makeText(MainActivity.this, "Alterado", Toast.LENGTH_SHORT).show();
            }
                return super.onDoubleTap(e);
        }


        @Override
        public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY) {
            boolean result = false;
            if(editControl) {
                float diffY = moveEvent.getY() - downEvent.getY();
                float diffX = moveEvent.getX() - downEvent.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > 100 && Math.abs(velocityX) > 100) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result = true;
                    }
                } else {
                    if (Math.abs(diffY) > 100 && Math.abs(velocityY) > 100) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                        result = true;
                    }
                }
            }
            return result;
        }

        private void onSwipeTop() {
            if(editControl) {
                credencial.setNome(edtNomeProg.getText().toString());
                credencial.setUsuario(edtUsuarioProg.getText().toString());
                credencial.setSenha(edtSenhaProg.getText().toString());
                bd = new bdModel(getApplicationContext());
                bd.insert(bdModel.getTabela(), credencial);
                carregarRegistroZero();
                Toast.makeText(MainActivity.this, "Cadastrado", Toast.LENGTH_SHORT).show();
            }
        }

        private void onSwipeBottom() {
            if(editControl) {
                limpar();
                Toast.makeText(MainActivity.this, "Novo", Toast.LENGTH_SHORT).show();
            }
        }

        private void onSwipeLeft() {
            if(editControl) {
                    if (quantidadeRegistros != 0) {
                        if (registroAtual < quantidadeRegistros - 1) {
                            registroAtual = registroAtual + 1;
                            carregarDados(registroAtual);
                        }
                    }
                }
        }

        private void onSwipeRight() {
                if (editControl) {
                if (quantidadeRegistros != 0) {
                    if (registroAtual > 0) {
                        registroAtual = registroAtual - 1;
                        carregarDados(registroAtual);
                    }
                }
            }
        }
    }

    public void clickBtnEditar(View v) {
        editControl = !editControl;
        if(editControl){
            disableEditText();
        }
        else{
            enableEditText();
        }
    }

    private void disableEditText() {
        edtNomeProg.setFocusable(false);
        edtNomeProg.setEnabled(false);
        edtNomeProg.setCursorVisible(false);
        edtNomeProg.setBackgroundColor(Color.TRANSPARENT);

        edtUsuarioProg.setFocusable(false);
        edtUsuarioProg.setEnabled(false);
        edtUsuarioProg.setCursorVisible(false);
        edtUsuarioProg.setBackgroundColor(Color.TRANSPARENT);

        edtSenhaProg.setFocusable(false);
        edtSenhaProg.setEnabled(false);
        edtSenhaProg.setCursorVisible(false);
        edtSenhaProg.setBackgroundColor(Color.TRANSPARENT);
    }

    private void enableEditText() {
        edtNomeProg.setFocusableInTouchMode(true);
        edtNomeProg.setEnabled(true);
        edtNomeProg.setCursorVisible(true);
        edtNomeProg.requestFocus();
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .showSoftInput(edtNomeProg, 0);

        edtUsuarioProg.setFocusableInTouchMode(true);
        edtUsuarioProg.setEnabled(true);
        edtUsuarioProg.setCursorVisible(true);

        edtSenhaProg.setFocusableInTouchMode(true);
        edtSenhaProg.setEnabled(true);
        edtSenhaProg.setCursorVisible(true);
    }

    public void limpar(){
        edtNomeProg.setText("");
        edtUsuarioProg.setText("");
        edtSenhaProg.setText("");
        txtServicoProg.setText("Serviço:");
        registroAtual = 0;
    }

    public void carregarDados(int i) {
        bd = new bdModel(getApplicationContext());
        ArrayList<credencialModel> arrayCredencialModel;
        arrayCredencialModel = bd.select();
        quantidadeRegistros = arrayCredencialModel.size();
        if(quantidadeRegistros != 0){
            credencialModel credencialModel = arrayCredencialModel.get(i);
            txtServicoProg.setText("Serviço " + String.valueOf(credencialModel.getId()) + ":");
            idCredencialAtual = credencialModel.getId();
            edtNomeProg.setText(credencialModel.getNome());
            edtUsuarioProg.setText(credencialModel.getUsuario());
            edtSenhaProg.setText(credencialModel.getSenha());
        }
    }

    public void carregarRegistroZero(){
        registroAtual = 0;
        carregarDados(registroAtual);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}