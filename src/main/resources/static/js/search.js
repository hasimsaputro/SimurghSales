let searchInput = document.querySelector('input.searchDataLeads');
let suggestionsContainer = document.querySelector('.search-container .suggestions');
let selectFilter = document.querySelector('.filter select')
let urlDataleads = 'http://localhost:8082/api/dataLeads/';

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

document.addEventListener('click',function(event){
    if(!event.target.closest('.search-container')){
        suggestionsContainer.innerHTML = '';
    }
})