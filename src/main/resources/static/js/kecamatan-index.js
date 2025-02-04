(() => {
    const mainUrl = "http://localhost:8082/api/kecamatan";
    const searchInput = document.querySelector("#search");
    const suggestions = document.querySelector(".search-container-filter .suggestions");
    const selectFilter = document.querySelector("#filter");

    searchInput.addEventListener('input', function() {
        const query = searchInput.value.toLowerCase();
        const filterValue = selectFilter.value;

        suggestions.innerHTML = '';
        if (query.length < 1) return;

        fetch(`${mainUrl}/getSearchItems=${filterValue}`)
            .then(response => response.json())
            .then(data => {
                const items = data.map(option => option.name);
                const filteredData = items.filter(item => item.toLowerCase().includes(query));

                if (filteredData.length > 0) {
                    suggestions.style.display = 'block';
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
                } else {
                    suggestions.style.display = 'none';
                }
            })
            .catch(error => {
                console.error('Error fetching data:', error);
            });
    });

    document.addEventListener('click', function(event) {
        if (!event.target.closest('.search-container-filter')) {
            suggestions.innerHTML = '';
        }
    });

})();
