const BASE_URL = "http://localhost:8080/newRestApi";
function fetchDataAndSetDataTable(dataUrl, contentType, accept) {
    // Fetch data from the server
    fetch(BASE_URL + dataUrl, {
		headers : {
			'Content-Type': contentType,
			Accept: accept
		}
	})
        .then(response => response.text()) // Convert response to text
        .then(data => {
            // Determine the content type and call the appropriate parsing function
            if (accept.includes("text/xml")) {
                parseXmlData(data);
            } else if (accept.includes("application/json")) {
                parseJsonData(data);
            } else if (accept.includes("text/string")) {
                parseStringData(data);
            }
        })
        .catch(error => console.error('Error fetching data:', error));
}

// Function to handle editing a film
function editFilm(id) {
	const format = document.getElementById('format').value;
    fetch(BASE_URL + '/api/films?id=' + id , {
		headers : {
			'Content-Type': format,
				Accept: format
			}
		})
        .then(response => response.text()) 
        .then(film => {
			let parsedFilm;
            // Parse film data based on the format received
            if (format.includes("text/xml")) {
                parsedFilm = parseSingleXmlFilmData(film);
            } else if (format.includes("application/json")) {
                parsedFilm = parseJsonFilmData(film);
            } else if (format.includes("text/string")) {
                parsedFilm = parseSingleStringFilmData(film);
            }
            populateEditForm(parsedFilm);
            $('#myModal').modal('show'); 
            })
        .catch(error => console.error('Error fetching film data:', error));
}

function handleDelete() {
	const id = document.getElementById('delete-id').value;
	 fetch(BASE_URL + '/api/films?id=' + id , {
		method: 'DELETE',
		headers : {
			'Content-Type': format,
				Accept: format
			}
		})
        .then(response => response.text()) 
        .then(data => {
			changeFormat();
            console.log('Data sent successfully:', data);
            $.toast({
			    heading: 'Success',
			    text: 'Film Deleted Successfully',
			    position: 'top-center',
			    stack: false,
			    icon: 'success'
			});
			$('#deleteModal').modal('hide'); 
        })
        .catch(error => {
            console.error('Error sending data:', error);
            $.toast({
			    heading: 'Error',
			    text: error,
			    position: 'top-center',
			    stack: false,
			    icon: 'error'
			})
        });
}

function deleteFilm(id) {
	$('#deleteModal').modal('show');
	document.getElementById('delete-id').value = id;
}


// Function to handle editing a film
function addFilm() {
	document.getElementById('modal-title').textContent = 'Add Film';
    document.getElementById('id').value = "";
    document.getElementById('title').value = "";
    document.getElementById('director').value = "";
    document.getElementById('stars').value = "";
    document.getElementById('review').value = "";
    document.getElementById('year').value = "";
    $('#myModal').modal('show');
    $('form').get(0).reset()
}

function submitForm() {
	const format = document.getElementById('format').value;
	const mode = document.getElementById('modal-title').textContent;
	if(mode.includes('Edit')) {
		const film = {
			id: document.getElementById('id').value,
		    title: document.getElementById('title').value,
		    director: document.getElementById('director').value,
		    stars: document.getElementById('stars').value,
		    review: document.getElementById('review').value,
		    year: document.getElementById('year').value,
		}
		let data;
		if(format.includes('text/xml')) {
			 data = `<Film>${jsonToXml(film)}</Film>`;
		} else if(format.includes('text/string')) {
			data = jsonToString(film);
		} else {
			data = JSON.stringify(film);
		}
		fetch(BASE_URL + '/api/films', {
			method: 'PUT',
			headers: {
				'Content-Type': format,
				Accept: format,
			},
			body: data
		})
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response;
        })
        .then(data => {
			changeFormat();
            console.log('Data sent successfully:', data);
            $.toast({
			    heading: 'Success',
			    text: 'Film Updated Successfully',
			    position: 'top-center',
			    stack: false,
			    icon: 'success'
			});
			$('#myModal').modal('hide'); 
        })
        .catch(error => {
            console.error('Error sending data:', error);
            $.toast({
			    heading: 'Error',
			    text: error,
			    position: 'top-center',
			    stack: false,
			    icon: 'error'
			})
        });
	} else {
		const film = {
		    title: document.getElementById('title').value,
		    director: document.getElementById('director').value,
		    stars: document.getElementById('stars').value,
		    review: document.getElementById('review').value,
		    year: document.getElementById('year').value,
		}
		let data;
		if(format.includes('text/xml')) {
			data = `<Film>${jsonToXml(film)}</Film>`;
		} else if(format.includes('text/string')) {
			data = jsonToString(film);
		} else {
			data = JSON.stringify(film);
		}
		fetch(BASE_URL + '/api/films', {
			method: 'POST',
			headers: {
				'Content-Type': format,
				Accept: format,
			},
			body: data
		})
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response;
        })
        .then(data => {
			changeFormat();
            console.log('Data sent successfully:', data);
            $.toast({
			    heading: 'Success',
			    text: 'Film Saved Successfully',
			    position: 'top-center',
			    stack: false,
			    icon: 'success'
			})
			$('#myModal').modal('hide'); 
        })
        .catch(error => {
            console.error('Error sending data:', error);
            $.toast({
			    heading: 'Error',
			    text: error,
			    position: 'top-center',
			    stack: false,
			    icon: 'error'
			})
        });
	}
}


// Example starter JavaScript for disabling form submissions if there are invalid fields
(function () {
  'use strict'

  // Fetch all the forms we want to apply custom Bootstrap validation styles to
  var forms = document.querySelectorAll('.needs-validation')

  // Loop over them and prevent submission
  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }

        form.classList.add('was-validated')
      }, false)
    })
})()
function parseXmlData(xmlString) {
    const parser = new DOMParser();
    const xmlDoc = parser.parseFromString(xmlString, 'text/xml');
    const filmsXml = xmlDoc.querySelectorAll('Film');
    const films = Array.from(filmsXml).map(film => ({
        id: film.querySelector('id').textContent,
        title: film.querySelector('title').textContent,
        director: film.querySelector('director').textContent,
        stars: film.querySelector('stars').textContent,
        review: film.querySelector('review').textContent,
        year: film.querySelector('year').textContent
    }));
    populateDataTable(films);
}

function parseSingleXmlFilmData(xmlString) {
    const parser = new DOMParser();
    const xmlDoc = parser.parseFromString(xmlString, 'text/xml');
    const filmsXml = xmlDoc.querySelectorAll('Film');
    const films = {
        id: filmsXml[0].querySelector('id').textContent,
        title: filmsXml[0].querySelector('title').textContent,
        director: filmsXml[0].querySelector('director').textContent,
        stars: filmsXml[0].querySelector('stars').textContent,
        review: filmsXml[0].querySelector('review').textContent,
        year: filmsXml[0].querySelector('year').textContent
    };
    populateEditForm(films);
    return films;
}

function parseJsonData(jsonString) {
    const films = JSON.parse(jsonString);
    populateDataTable(films);
}

function parseStringData(stringData) {
    const filmsStr = stringData.split('|');
    const films = filmsStr.map(str => {
        const props = str.split('#');
        const film = {};
        props.forEach(prop => {
            const [key, value] = prop.split(':');
            film[key] = value;
        });
        return film;
    });
    populateDataTable(films);
}

// Function to parse XML film data
function parseXmlFilmData(data) {
    const parser = new DOMParser();
    const xmlDoc = parser.parseFromString(data, 'text/xml');
    const filmsXml = xmlDoc.querySelectorAll('Film');
    const films = Array.from(filmsXml).map(film => ({
        id: film.querySelector('id').textContent,
        title: film.querySelector('title').textContent,
        director: film.querySelector('director').textContent,
        stars: film.querySelector('stars').textContent,
        review: film.querySelector('review').textContent,
        year: film.querySelector('year').textContent
    }));
    return films;
}

// Function to parse JSON film data
function parseJsonFilmData(data) {
    return JSON.parse(data);
}

// Function to parse string film data
function parseStringFilmData(data) {
    const filmsStr = data.split('|');
    const films = filmsStr.map(str => {
        const props = str.split('#');
        const film = {};
        props.forEach(prop => {
            const [key, value] = prop.split(':');
            film[key] = value;
        });
        return film;
    });
    return films;
}

function parseSingleStringFilmData(data) {
    const props = data.split('#');
    const film = {};
    props.forEach(prop => {
        const [key, value] = prop.split(':');
        film[key] = value;
    });
    return film;
}

function populateEditForm(film) {
    // Assuming you have form fields in your edit form with appropriate IDs
    document.getElementById('modal-title').textContent = 'Edit Film';
    document.getElementById('id').value = film.id;
    document.getElementById('title').value = film.title;
    document.getElementById('director').value = film.director;
    document.getElementById('stars').value = film.stars;
    document.getElementById('review').value = film.review;
    document.getElementById('year').value = film.year;
}

function jsonToXml(json) {
    let xml = '';
    for (let key in json) {
        if (json.hasOwnProperty(key)) {
            xml += `<${key}>`;
            if (typeof json[key] === 'object') {
                xml += jsonToXml(json[key]);
            } else {
                xml += json[key];
            }
            xml += `</${key}>`;
        }
    }

    return xml;
}

function jsonToString(json) {
    let str = '';
    for (let key in json) {
        if (json.hasOwnProperty(key)) {
            str += `${key}:${json[key]}#`;
        }
    }
    // Remove the trailing '#' character
    str = str.slice(0, -1);
    return str;
}

function populateDataTable(films) {
    $('#dataTable').DataTable({
		columnDefs: [{
		    "defaultContent": "-",
		    "targets": "_all"
		  }],
        data: films,
        columns: [
            { data: 'id' },
            { data: 'title' },
            { data: 'director' },
            { data: 'stars' },
            { 
				data: 'review',
	            render: function ( data, _type, _row ) {
	            return data.substr( 0, 20 ) +'…';
	        }
             },
            { data: 'year' },
             {
                // Add custom HTML for edit button
                data: null,
                render: function (_data, _type, row, _meta) {
                    return `<div class="btn-group" role="group" aria-label="Basic mixed styles example">
		  				<button class="btn btn-warning btn-sm" onclick="editFilm('${row.id}')"><i class="fa fa-pencil" aria-hidden="true"></i></button>
		  				<button class="btn btn-danger btn-sm" onclick="deleteFilm('${row.id}')"><i class="fa fa-trash" aria-hidden="true"></i></button>
					</div>`
                    ;
                }
            }
        ],
        "bDestroy": true
    });
}

const changeFormat = () => {
	fetchDataAndSetDataTable('/api/films', document.getElementById('format').value, document.getElementById('format').value);
}
// Call the function with the URL of the data source
fetchDataAndSetDataTable('/api/films',document.getElementById('format').value, document.getElementById('format').value);
