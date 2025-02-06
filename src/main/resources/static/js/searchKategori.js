let urlKategori = 'http://localhost:8082/api/kategori/';
let searchKategori = document.querySelector('input#search.searchKategori');
let suggestionsKategoriContainer = document.querySelector('.search-container-filter .suggestions.kategori');
let selectKategoriFilter = document.querySelector('.filter.kategori select');

if(searchKategori){
  searchKategori.addEventListener('input', function() {
      const query = searchKategori.value.toLowerCase();
      const filterValue = selectKategoriFilter.value;
      suggestionsKategoriContainer.innerHTML = '';
      fetch(`${urlKategori}getSearchItems=${filterValue}`)
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
                              searchKategori.value = item;
                              suggestionsKategoriContainer.innerHTML = '';
                          });

                          suggestionsKategoriContainer.appendChild(div);
                              });
                          }
                      })
                      .catch(error => {
                          console.error('Error fetching data:', error);
                      });
  });
}

