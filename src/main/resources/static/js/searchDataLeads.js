let urlDataleads = 'http://localhost:8082/api/dataLeads/';
let searchInput = document.querySelector('input.searchDataLeads');
let suggestionsContainer = document.querySelector('.search-container .suggestions');
let selectFilter = document.querySelector('.filter select');

let kelurahanInput = document.querySelector('input#kelurahan.searchFormKelurahan');
let suggestionKelurahan = document.querySelector('div.suggestionDataLeadsKelurahan')

let potInput = document.querySelector('input#pot.searchFormPOT');
let suggestionPot = document.querySelector('div.suggestionDataLeadsPOT')

if(searchInput){
    searchInput.addEventListener('input', function() {
        const query = searchInput.value.toLowerCase();
        const filterValue = selectFilter.value;
        console.log(filterValue);
        suggestionsContainer.innerHTML = '';
        fetch(`${urlDataleads}getSearchItems=${filterValue}`)
                .then(response => response.json())
                .then(data => {
                    const items = data.map(option => option.text);
                    console.log(items)
                    if (query) {
                        const filteredData = items.filter(item => item.toLowerCase().includes(query));
                        console.log(filteredData)
                        filteredData.forEach(item => {
                            const div = document.createElement('div');
                            div.classList.add('suggestion-item');
                            div.textContent = item;

                            div.addEventListener('click', function() {
                                searchInput.value = item;
                                suggestionsContainer.innerHTML = '';
                            });

                            suggestionsContainer.appendChild(div);
                        });
                    }
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
    });
}

kelurahanInput.addEventListener('input', ()=>{
    console.log('HALOOO');
    let kelurahanValue = kelurahanInput.value;
    suggestionKelurahan.innerHTML = '';
    fetch(`${urlDataleads}getKelurahan`)
                .then(response => response.json())
                .then(data => {
                    const items = data.map(option => option.text);
                    console.log(items)
                    if (kelurahanValue) {
                        const filteredData = items.filter(item => item.toLowerCase().includes(kelurahanValue));
                        console.log(filteredData)
                        filteredData.forEach(item => {
                            const div = document.createElement('div');
                            div.classList.add('suggestion-item');
                            div.textContent = item;

                            div.addEventListener('click', function() {
                                kelurahanInput.value = item;
                                suggestionKelurahan.innerHTML = '';
                            });

                            suggestionKelurahan.appendChild(div);
                        });
                    }
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
})

potInput.addEventListener('input',()=>{
    console.log('halooo')
    let potInputValue = potInput.value.toLowerCase());
    suggestionPot.innerHTML = '';
    fetch(`${urlDataleads}getPOT`)
                .then(response => response.json())
                .then(data => {
                    const items = data.map(option => option.text);
                    console.log(items)
                    if (potInputValue) {
                        const filteredData = items.filter(item => item.toLowerCase().includes(potInputValue));
                        console.log(filteredData)
                        filteredData.forEach(item => {
                            const div = document.createElement('div');
                            div.classList.add('suggestion-item');
                            div.textContent = item;

                            div.addEventListener('click', function() {
                                potInput.value = item;
                                suggestionPot.innerHTML = '';
                            });

                            suggestionPot.appendChild(div);
                        });
                    }
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
})

potInput.addEventListener('keydown',(event)=>{
    if(event.key == 'Enter'){
        console.log('Halo Kau Enter');
    }
})

document.addEventListener('click',function(event){
    if(!event.target.closest('.search-container')){
        suggestionsContainer.innerHTML = '';
    }
})