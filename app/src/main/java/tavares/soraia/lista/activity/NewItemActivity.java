package tavares.soraia.lista.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import tavares.soraia.lista.R;
import tavares.soraia.lista.model.NewItemActivityViewModel;

public class NewItemActivity extends AppCompatActivity {
    // Definindo um código para a solicitação do seletor de fotos
    static int PHOTO_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Habilitando o layout de recorte de bordas
        EdgeToEdge.enable(this);
        // Definindo o layout da atividade
        setContentView(R.layout.activity_new_item);
        // Configurando o recorte de bordas para a área principal da atividade
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        NewItemActivityViewModel vm = new ViewModelProvider(this).get(NewItemActivityViewModel.class);

        Uri selectPhotoLocation = vm.getSelectPhotoLocation();
        if (selectPhotoLocation != null) {
            ImageView imvfotoPreview = findViewById(R.id.imvPhotoPreview);
            imvfotoPreview.setImageURI(selectPhotoLocation);
        }


        // Configurando o ImageButton para abrir o seletor de fotos ao ser clicado
        ImageButton imgCI = findViewById(R.id.imbCl);
        imgCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Criando uma intenção para abrir o seletor de fotos
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                // Iniciando a atividade esperando um resultado
                startActivityForResult(photoPickerIntent, PHOTO_PICKER_REQUEST);
            }
        });

        // Configurando o botão de adicionar item para processar os dados inseridos
        Button btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri selectPhotoLocation = vm.getSelectPhotoLocation();
                // Verificando se uma foto foi selecionada
                if (selectPhotoLocation == null) {
                    Toast.makeText(NewItemActivity.this, "É necessário selecionar uma imagem!", Toast.LENGTH_LONG).show();
                    return;
                }
                // Obtendo o título inserido pelo usuário
                EditText etTitle = findViewById(R.id.etTitle);
                String title = etTitle.getText().toString();
                // Verificando se o título está vazio
                if (title.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É necessário inserir um título", Toast.LENGTH_LONG).show();
                    return;
                }
                // Obtendo a descrição inserida pelo usuário
                EditText etDesc = findViewById(R.id.etDesc);
                String description = etDesc.getText().toString();
                // Verificando se a descrição está vazia
                if (description.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É necessário inserir uma descrição", Toast.LENGTH_LONG).show();
                    return;
                }
                // Criando uma intenção para retornar os dados inseridos à atividade anterior
                Intent i = new Intent();
                i.setData(selectPhotoLocation);
                i.putExtra("title", title);
                i.putExtra("description", description);
                // Configurando o resultado como bem-sucedido e passando os dados
                setResult(Activity.RESULT_OK, i);
                // Finalizando esta atividade e retornando à anterior
                finish();
            }
        });
    }

    // Método para lidar com o resultado do seletor de fotos
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Verificando se o resultado é do seletor de fotos
        if (requestCode == PHOTO_PICKER_REQUEST) {
            // Verificando se o resultado é bem-sucedido
            if (resultCode == Activity.RESULT_OK) {
                // Armazenando a URI da foto selecionada
                Uri photoSelected = data.getData();
                // Exibindo a pré-visualização da foto selecionada
                ImageView imvfotoPreview = findViewById(R.id.imvPhotoPreview);
                imvfotoPreview.setImageURI(photoSelected);
                NewItemActivityViewModel vm = new ViewModelProvider(this)
                        .get(NewItemActivityViewModel.class);
                vm.setSelectPhotoLocation(photoSelected);
            }
        }
    }
}
