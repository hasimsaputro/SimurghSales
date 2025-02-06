(()=>{
    let mainUrl = "http://localhost:8082/api/cabang";

    let searchInput = document.querySelector("#search");
    let suggestions = document.querySelector(".search-container-filter .suggestions");
    let selectFilter = document.querySelector("#filter");

    searchInput.addEventListener('input', function() {
        const query = searchInput.value.toLowerCase();
        const filterValue = selectFilter.value;
        console.log(filterValue);
        suggestions.innerHTML = '';
        fetch(`${mainUrl}/getSearchItems=${filterValue}`)
                .then(response => response.json())
                .then(data => {
                    const items = data.map(option => option.nama);
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
                                suggestions.innerHTML = '';
                            });

                            suggestions.appendChild(div);
                        });
                    }
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
    });

    document.addEventListener('click',function(event){
        if(!event.target.closest('.search-container')){
            suggestions.innerHTML = '';
        }
    })
})()