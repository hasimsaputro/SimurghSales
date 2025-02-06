document.addEventListener('DOMContentLoaded', function() {
    let urlMerk = 'http://localhost:8082/api/merk/';
    let searchMerk = document.querySelector('input#search.searchIndexMerk');
    let suggestionsMerkContainer = document.querySelector('.search-container-filter .suggestions.merk');
    let selectMerkFilter = document.querySelector('.filter.merk select');

    if(searchMerk){
      searchMerk.addEventListener('input', function() {
          const query = searchMerk.value.toLowerCase();
          const filterValue = selectMerkFilter.value;
          suggestionsMerkContainer.innerHTML = '';
          fetch(`${urlMerk}getSearchItems=${filterValue}`)
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
                                  searchMerk.value = item;
                                  suggestionsMerkContainer.innerHTML = '';
                              });

                              suggestionsMerkContainer.appendChild(div);
                                  });
                              }
                          })
                          .catch(error => {
                              console.error('Error fetching data:', error);
                          });
              });
          }

    let kategoriMerkInput = document.querySelector('input#namaKategoriMerk.namaKategoriMerkInput');
    let idKategoriMerk = document.querySelector('input#idKategoriMerk.idKategoriMerkInput');
    let suggestionsKategoriMerk = document.querySelector('.search-container .suggestions.merkKategori');

    if(kategoriMerkInput && suggestionsKategoriMerk){
        kategoriMerkInput.addEventListener('input',()=>{
            const query = kategoriMerkInput.value.toLowerCase();
            suggestionsKategoriMerk.innerHTML = '';
            fetch(`${urlMerk}getKategori`)
              .then(response => response.json())
              .then(data => {
                  const items = data.map(option => option.text);
                  const idKategori = data.map(option => option.value);
                  if (query) {
                      const filteredData = items.filter(item => item.toLowerCase().includes(query));
                      filteredData.forEach(item => {
                          const div = document.createElement('div');
                          div.classList.add('suggestion-item');
                          div.textContent = item;

                          div.addEventListener('click', function() {
                              kategoriMerkInput.value = item;
                              idKategoriMerk.value = data.find(option => option.text === item)?.value;
                              suggestionsKategoriMerk.innerHTML = '';
                          });

                          suggestionsKategoriMerk.appendChild(div);
                      });
                  }
              })
              .catch(error => {
                  console.error('Error fetching data:', error);
              });
            });
    }


    let negaraMerkInput = document.querySelector('input#namaNegaraMerk.namaNegaraMerkInput');
    let idNegaraMerk = document.querySelector('input#idNegaraMerk.idNegaraMerkInput');
    let suggestionsNegaraMerk = document.querySelector('.search-container .suggestions.merkNegara');

    if(negaraMerkInput && suggestionsNegaraMerk){
            negaraMerkInput.addEventListener('input',()=>{
                const query = negaraMerkInput.value.toLowerCase();
                suggestionsNegaraMerk.innerHTML = '';
                fetch(`${urlMerk}getNegara`)
                  .then(response => response.json())
                  .then(data => {
                      const items = data.map(option => option.text);
                      const idNegara = data.map(option => option.value);
                      if (query) {
                          const filteredData = items.filter(item => item.toLowerCase().includes(query));
                          filteredData.forEach(item => {
                              const div = document.createElement('div');
                              div.classList.add('suggestion-item');
                              div.textContent = item;

                              div.addEventListener('click', function() {
                                  negaraMerkInput.value = item;
                                  idNegaraMerk.value = data.find(option => option.text === item)?.value;
                                  suggestionsNegaraMerk.innerHTML = '';
                              });

                              suggestionsNegaraMerk.appendChild(div);
                          });
                      }
                  })
                  .catch(error => {
                      console.error('Error fetching data:', error);
                  });
                });
        }
})