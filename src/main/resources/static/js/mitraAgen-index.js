(()=>{
    let mainUrl = "http://localhost:8082/api/mitraAgen";

    let searchInput = document.querySelector("#search");
    let suggestions = document.querySelector(".suggestions .search-container");
    let selectFilter = document.querySelector("#filter");

    searchInput.addEventListener('input', => (){
        const query = searchInput.value.toLowerCase();
        const filterValue = selectFilter.value;

        suggestions.innerHTML = '';
        fetch(`${mainUrl}/getSearchItems={filterValue}`)
            .then(response = response.json())
            .then(data => {
                const items = data.map(option => option.text);
                if (query){
                    const filteredData = items.filter(item => item.toLowerCase().includes(query));
                    filteredData.forEach(item => {
                        const div = document.createElement('div');
                        div.classList.add('suggestion-item');
                        div.textContent = item;

                        div.addEventListener('click' => () {
                            searchInput.value = item;
                            suggestions.innerHTML = '';
                        });

                        suggestions.appendChild(div);
                    })
                }
            })
            .catch(error => {
                console.error('Error fetching data:', error);
            })
    })

    document.addEventListener('click' => (){
        if(!event.target.closest('.search-container')){
            suggestions.innerHTML = '';
        }
    })
})()