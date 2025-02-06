let urlHarga = 'http://localhost:8082/api/hargaPasar/';

let kategoriHargaInput = document.querySelector('input#namaKategoriHarga.namaKategoriHargaInput');
let idKategoriHargaInput = document.querySelector('input#idKategoriHarga.idKategoriHargaInput');
let suggestionsKategoriHarga = document.querySelector('.search-container .suggestions.kategoriHargaPasar');

if(kategoriHargaInput && suggestionsKategoriHarga){
  kategoriHargaInput.addEventListener('input',()=>{
      const query = kategoriHargaInput.value.toLowerCase();
      suggestionsKategoriHarga.innerHTML = '';
      fetch(`${urlHarga}getKategori`)
        .then(response => response.json())
        .then(data => {
            const items = data.map(option => option.text);
            if (query) {
                const filteredData = items.filter(item => item.toLowerCase().includes(query));
                filteredData.forEach(item => {
                    const div = document.createElement('div');
                    div.classList.add('suggestion-item');
                    div.textContent = item;

                    div.addEventListener('click', function() {
                        kategoriHargaInput.value = item;
                        idKategoriHargaInput.value = data.find(option => option.text === item)?.value;
                        suggestionsKategoriHarga.innerHTML = '';
                    });

                    suggestionsKategoriHarga.appendChild(div);
                });
            }
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
      });
}

let wilayahHargaInput = document.querySelector('input#namaWilayahHarga.namaWilayahHargaInput');
let idWilayahHarga = document.querySelector('input#idWilayahHarga.idWilayahHargaInput');
let suggestionsWilayahHarga = document.querySelector('.search-container .suggestions.wilayahHargaPasar');

if(wilayahHargaInput && suggestionsWilayahHarga){
    wilayahHargaInput.addEventListener('input',()=>{
        const query = wilayahHargaInput.value.toLowerCase();
        suggestionsWilayahHarga.innerHTML = '';
        fetch(`${urlHarga}getWilayah`)
          .then(response => response.json())
          .then(data => {
              const items = data.map(option => option.text);
              if (query) {
                  const filteredData = items.filter(item => item.toLowerCase().includes(query));
                  filteredData.forEach(item => {
                      const div = document.createElement('div');
                      div.classList.add('suggestion-item');
                      div.textContent = item;

                      div.addEventListener('click', function() {
                          wilayahHargaInput.value = item;
                          idWilayahHarga.value = data.find(option => option.text === item)?.value;
                          suggestionsWilayahHarga.innerHTML = '';
                      });

                      suggestionsWilayahHarga.appendChild(div);
                  });
              }
          })
          .catch(error => {
              console.error('Error fetching data:', error);
          });
        });
}

let merkHargaInput = document.querySelector('input#namaMerkHarga.namaMerkHargaInput');
let idMerkHarga = document.querySelector('input#idMerkHarga.idMerkHargaInput');
let suggestionsMerkHarga = document.querySelector('.search-container .suggestions.merkHargaPasar');

if(merkHargaInput && suggestionsMerkHarga){
merkHargaInput.addEventListener('input',()=>{
    const query = merkHargaInput.value.toLowerCase();
    suggestionsMerkHarga.innerHTML = '';
    fetch(`${urlHarga}getMerk`)
      .then(response => response.json())
      .then(data => {
          const items = data.map(option => option.text);
          if (query) {
              const filteredData = items.filter(item => item.toLowerCase().includes(query));
              filteredData.forEach(item => {
                  const div = document.createElement('div');
                  div.classList.add('suggestion-item');
                  div.textContent = item;

                  div.addEventListener('click', function() {
                      merkHargaInput.value = item;
                      idMerkHarga.value = data.find(option => option.text === item)?.value;
                      suggestionsMerkHarga.innerHTML = '';
                  });

                  suggestionsMerkHarga.appendChild(div);
              });
          }
      })
      .catch(error => {
          console.error('Error fetching data:', error);
      });
    });
}

let modelHargaInput = document.querySelector('input#namaModelHarga.namaModelHargaInput');
let idModelHarga = document.querySelector('input#idModelHarga.idModelHargaInput');
let suggestionsModelHarga = document.querySelector('.search-container .suggestions.modelHargaPasar');

if(modelHargaInput && suggestionsModelHarga){
modelHargaInput.addEventListener('input',()=>{
    const query = modelHargaInput.value.toLowerCase();
    suggestionsModelHarga.innerHTML = '';
    fetch(`${urlHarga}getModel`)
      .then(response => response.json())
      .then(data => {
          const items = data.map(option => option.text);
          if (query) {
              const filteredData = items.filter(item => item.toLowerCase().includes(query));
              filteredData.forEach(item => {
                  const div = document.createElement('div');
                  div.classList.add('suggestion-item');
                  div.textContent = item;

                  div.addEventListener('click', function() {
                      modelHargaInput.value = item;
                      idModelHarga.value = data.find(option => option.text === item)?.value;
                      suggestionsModelHarga.innerHTML = '';
                  });

                  suggestionsModelHarga.appendChild(div);
              });
          }
      })
      .catch(error => {
          console.error('Error fetching data:', error);
      });
    });
}


let tipeHargaInput = document.querySelector('input#namaTipeHarga.namaTipeHargaInput');
let idTipeHarga = document.querySelector('input#idTipeHarga.idTipeHargaInput');
let suggestionsTipeHarga = document.querySelector('.search-container .suggestions.tipeHargaPasar');

let jenisHargaInput = document.querySelector('input#namaJenisHarga.namaJenisHargaInput');
let idJenisHarga = document.querySelector('input#idJenisHarga.idJenisHargaInput');

if(tipeHargaInput && suggestionsTipeHarga){
tipeHargaInput.addEventListener('input',()=>{
    const query = tipeHargaInput.value.toLowerCase();
    suggestionsTipeHarga.innerHTML = '';
    fetch(`${urlHarga}getTipe`)
      .then(response => response.json())
      .then(data => {
          const items = data.map(option => option.text);
          if (query) {
              const filteredData = items.filter(item => item.toLowerCase().includes(query));
              filteredData.forEach(item => {
                  const div = document.createElement('div');
                  div.classList.add('suggestion-item');
                  div.textContent = item;

                  div.addEventListener('click', function() {
                      tipeHargaInput.value = item;
                      idTipeHarga.value = data.find(option => option.text === item)?.value;
                      const idTipeValue = data.find(option => option.text === item)?.value;
                      suggestionsTipeHarga.innerHTML = '';

                      fetch(`${urlHarga}getTipeJenis=${idTipeValue}`)
                          .then(response => response.json())
                          .then(data => {
                              jenisHargaInput.value = data.text;
                              idJenisHarga.value = data.value;
                          })
                          .catch(error => {
                              console.error('Error fetching data:', error);
                          });
                  });

                  suggestionsTipeHarga.appendChild(div);
              });
          }
      })
      .catch(error => {
          console.error('Error fetching data:', error);
      });
    });
}

let tipeUnitInput = document.querySelector('input#namaTipeUnitHarga.namaTipeUnitHargaInput');
let idTipeUnit = document.querySelector('input#idTipeUnitHarga.idTipeUnitHargaInput');
let suggestionsTipeUnit = document.querySelector('.search-container .suggestions.tipeUnitHargaPasar');

if(tipeUnitInput && suggestionsTipeUnit){
tipeUnitInput.addEventListener('input',()=>{
    const query = tipeUnitInput.value.toLowerCase();
    suggestionsTipeUnit.innerHTML = '';
    fetch(`${urlHarga}getTipeUnit`)
      .then(response => response.json())
      .then(data => {
          const items = data.map(option => option.text);
          if (query) {
              const filteredData = items.filter(item => item.toLowerCase().includes(query));
              filteredData.forEach(item => {
                  const div = document.createElement('div');
                  div.classList.add('suggestion-item');
                  div.textContent = item;

                  div.addEventListener('click', function() {
                      tipeUnitInput.value = item;
                      idTipeUnit.value = data.find(option => option.text === item)?.value;
                      suggestionsTipeUnit.innerHTML = '';
                  });

                  suggestionsTipeUnit.appendChild(div);
              });
          }
      })
      .catch(error => {
          console.error('Error fetching data:', error);
      });
    });
}

